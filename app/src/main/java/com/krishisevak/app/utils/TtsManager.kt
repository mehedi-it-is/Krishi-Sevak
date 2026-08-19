package com.krishisevak.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Base64
import android.util.Log
import com.krishisevak.app.data.remote.sarvam.SarvamApi
import com.krishisevak.app.data.remote.sarvam.SarvamTtsRequest
import com.krishisevak.app.data.remote.tts.EdgeTtsClient
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.FileOutputStream
import java.util.Locale
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class TtsManager(
    private val context: Context,
    private var sarvamApi: SarvamApi? = null,
    private var sarvamApiKey: String = ""
) : TextToSpeech.OnInitListener {

    companion object {
        private const val TAG = "TtsManager"
    }

    private var localTts: TextToSpeech? = null
    private var isLocalTtsInitialized = false
    private val edgeTtsClient = EdgeTtsClient(context)

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _currentlySpeakingId = MutableStateFlow<String?>(null)
    val currentlySpeakingId: StateFlow<String?> = _currentlySpeakingId.asStateFlow()

    private val pendingCompletions = ConcurrentHashMap<String, CompletableDeferred<Unit>>()
    private val speakMutex = Mutex()
    private var currentTtsJob: Job? = null

    init {
        localTts = TextToSpeech(context.applicationContext, this)
    }

    fun setSarvamConfig(api: SarvamApi, key: String) {
        this.sarvamApi = api
        this.sarvamApiKey = key
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isLocalTtsInitialized = true
            Log.d(TAG, "Local TTS engine initialized successfully")
            localTts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _currentlySpeakingId.value = utteranceId
                }

                override fun onDone(utteranceId: String?) {
                    if (_currentlySpeakingId.value == utteranceId) {
                        _currentlySpeakingId.value = null
                    }
                    utteranceId?.let { id ->
                        pendingCompletions.remove(id)?.complete(Unit)
                    }
                }

                override fun onError(utteranceId: String?) {
                    Log.w(TAG, "Local TTS error for utteranceId=$utteranceId")
                    if (_currentlySpeakingId.value == utteranceId) {
                        _currentlySpeakingId.value = null
                    }
                    utteranceId?.let { id ->
                        pendingCompletions.remove(id)?.complete(Unit)
                    }
                }
            })
        } else {
            Log.e(TAG, "Local TTS init FAILED with status=$status")
        }
    }

    private fun mapToSarvamLanguage(languageCode: String): String {
        return when (languageCode.lowercase().trim()) {
            "hi", "hindi" -> "hi-IN"
            "bn", "bengali" -> "bn-IN"
            "mr", "marathi" -> "mr-IN"
            "te", "telugu" -> "te-IN"
            "ta", "tamil" -> "ta-IN"
            "kn", "kannada" -> "kn-IN"
            "ml", "malayalam" -> "ml-IN"
            "gu", "gujarati" -> "gu-IN"
            "pa", "punjabi" -> "pa-IN"
            "or", "od", "odia" -> "od-IN"
            "en", "english" -> "en-IN"
            else -> "hi-IN"
        }
    }

    private fun resolveLocale(languageCode: String): Locale {
        return when (languageCode.lowercase().trim()) {
            "hi", "hindi" -> Locale("hi", "IN")
            "bn", "bengali" -> Locale("bn", "IN")
            "kn", "kannada" -> Locale("kn", "IN")
            "ml", "malayalam" -> Locale("ml", "IN")
            "mr", "marathi" -> Locale("mr", "IN")
            "or", "od", "odia" -> Locale("or", "IN")
            "pa", "punjabi" -> Locale("pa", "IN")
            "ta", "tamil" -> Locale("ta", "IN")
            "te", "telugu" -> Locale("te", "IN")
            "gu", "gujarati" -> Locale("gu", "IN")
            "en", "english" -> Locale("en", "IN") // Indian English
            else -> Locale("en", "IN")
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun cleanTextForSpeech(raw: String): String {
        return raw
            .replace(Regex("[#*`_~]"), "")
            .replace(Regex("\\bhttps?://\\S+"), "")
            .replace(Regex("[🛡️🧪🔍⚖️🌾🩺📅📊📖🎓🎙️⚡📷🖼️💡✔️]"), "")
            .trim()
    }

    /**
     * Synthesize audio file with Sarvam AI bulbul:v3 (Speaker: ritu)
     */
    private suspend fun synthesizeWithSarvamBulbul(text: String, languageCode: String): File? = withContext(Dispatchers.IO) {
        val api = sarvamApi ?: return@withContext null
        if (sarvamApiKey.isBlank() || sarvamApiKey == "DEMO_SARVAM_KEY") return@withContext null

        try {
            val sarvamLang = mapToSarvamLanguage(languageCode)
            val clean = cleanTextForSpeech(text)
            if (clean.isBlank()) return@withContext null

            // Split text into chunks <= 450 characters (Sarvam Bulbul input limit per string)
            val chunks = mutableListOf<String>()
            if (clean.length <= 450) {
                chunks.add(clean)
            } else {
                val sentences = clean.split(Regex("(?<=[.!?।\n])\\s+"))
                var current = StringBuilder()
                for (sentence in sentences) {
                    if (current.length + sentence.length + 1 <= 450) {
                        if (current.isNotEmpty()) current.append(" ")
                        current.append(sentence)
                    } else {
                        if (current.isNotEmpty()) chunks.add(current.toString())
                        current = StringBuilder(sentence.take(450))
                    }
                }
                if (current.isNotEmpty()) chunks.add(current.toString())
            }

            val req = SarvamTtsRequest(
                inputs = chunks.take(5), // up to ~2250 characters
                targetLanguageCode = sarvamLang,
                speaker = "ritu",
                model = "bulbul:v3"
            )
            Log.d(TAG, "Requesting Sarvam Bulbul:v3 TTS for lang=$sarvamLang, chunksCount=${chunks.size}")
            val response = api.textToSpeech(sarvamApiKey, req)
            val base64Audio = response.audios?.firstOrNull()
            if (!base64Audio.isNullOrBlank()) {
                val audioBytes = Base64.decode(base64Audio, Base64.DEFAULT)
                val tempFile = File(context.cacheDir, "sarvam_bulbul_${UUID.randomUUID()}.wav")
                FileOutputStream(tempFile).use { it.write(audioBytes) }
                Log.d(TAG, "Sarvam Bulbul:v3 audio saved: ${tempFile.name}, size=${audioBytes.size} bytes")
                return@withContext tempFile
            } else {
                Log.w(TAG, "Sarvam Bulbul:v3 returned null or empty audio list")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Sarvam bulbul:v3 (speaker: ritu) TTS failed: ${e.message}", e)
        }
        return@withContext null
    }

    /**
     * Speaks aloud.
     * By default, uses native Android TTS engine directly (for all app features).
     * If useSarvamBulbul == true (used exclusively by AI Chatbot), synthesizes with Sarvam Bulbul:v3 with fallback to native TTS.
     */
    fun speak(id: String, text: String, languageCode: String, useSarvamBulbul: Boolean = false) {
        if (_currentlySpeakingId.value == id) {
            stop()
            return
        }

        stop()

        currentTtsJob = coroutineScope.launch {
            _currentlySpeakingId.value = id
            val cleanText = cleanTextForSpeech(text)
            if (cleanText.isBlank()) {
                _currentlySpeakingId.value = null
                return@launch
            }

            // If not requesting Sarvam Bulbul (or if offline), use Android native local TTS directly
            if (!useSarvamBulbul || !isNetworkAvailable()) {
                Log.d(TAG, "Using native Android TTS for id=$id in lang=$languageCode")
                speakWithLocalEngine(id, cleanText, languageCode)
                return@launch
            }

            // Synthesize with Sarvam AI bulbul:v3 (speaker: ritu) for AI Chatbot
            val sarvamAudioFile = try {
                withTimeout(15_000) {
                    synthesizeWithSarvamBulbul(cleanText, languageCode)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Sarvam Bulbul synthesis timed out or failed: ${e.message}")
                null
            }

            if (sarvamAudioFile != null && isActive) {
                Log.d(TAG, "Sarvam bulbul:v3 (ritu) SUCCESS — playing audio for id=$id in lang=$languageCode")
                edgeTtsClient.playAudio(
                    file = sarvamAudioFile,
                    onStart = { _currentlySpeakingId.value = id },
                    onCompletion = {
                        if (_currentlySpeakingId.value == id) {
                            _currentlySpeakingId.value = null
                        }
                    }
                )
                return@launch
            }

            // Fallback to native Android local TTS if online synthesis was unavailable
            if (isActive) {
                Log.d(TAG, "Bulbul v3 unavailable — falling back to native Android TTS for id=$id, lang=$languageCode")
                speakWithLocalEngine(id, cleanText, languageCode)
            }
        }
    }

    /**
     * Sequential/blocking speech.
     */
    suspend fun speakAndWait(id: String, text: String, languageCode: String, useSarvamBulbul: Boolean = false) {
        speakMutex.withLock {
            val deferred = CompletableDeferred<Unit>()
            pendingCompletions[id] = deferred

            val cleanText = cleanTextForSpeech(text)
            if (cleanText.isBlank()) {
                deferred.complete(Unit)
                return@withLock
            }

            // If not requesting Sarvam Bulbul (or offline), use Android native local TTS directly
            if (!useSarvamBulbul || !isNetworkAvailable()) {
                speakWithLocalEngine(id, cleanText, languageCode)
                try {
                    withTimeout(20_000) { deferred.await() }
                } catch (_: TimeoutCancellationException) {
                    pendingCompletions.remove(id)
                }
                return@withLock
            }

            // Try Sarvam AI bulbul:v3 (speaker: ritu)
            val sarvamAudioFile = try {
                withTimeout(15_000) {
                    synthesizeWithSarvamBulbul(cleanText, languageCode)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Sarvam Bulbul synthesis timed out or failed in speakAndWait: ${e.message}")
                null
            }

            if (sarvamAudioFile != null) {
                edgeTtsClient.playAudio(
                    file = sarvamAudioFile,
                    onStart = { _currentlySpeakingId.value = id },
                    onCompletion = {
                        if (_currentlySpeakingId.value == id) {
                            _currentlySpeakingId.value = null
                        }
                        pendingCompletions.remove(id)?.complete(Unit)
                    }
                )
            } else {
                // Fallback to native Android local TTS
                speakWithLocalEngine(id, cleanText, languageCode)
            }

            try {
                withTimeout(25_000) { deferred.await() }
            } catch (_: TimeoutCancellationException) {
                pendingCompletions.remove(id)
            }
        }
    }

    private fun speakWithLocalEngine(id: String, text: String, languageCode: String) {
        if (!isLocalTtsInitialized || localTts == null) {
            Log.w(TAG, "Local TTS not initialized, cannot speak id=$id")
            return
        }

        val locale = resolveLocale(languageCode)
        val result = localTts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.w(TAG, "Local TTS language $languageCode not supported, falling back to Indian English (en-IN)")
            val fallbackLocale = Locale("en", "IN")
            if (localTts?.setLanguage(fallbackLocale) == TextToSpeech.LANG_NOT_SUPPORTED) {
                localTts?.language = Locale.ENGLISH
            }
        }

        // Prefer Indian voices for Indian English (en-IN), Indian Bengali (bn-IN), etc.
        try {
            val targetVoice = localTts?.voices?.find { v ->
                v.locale.language.equals(locale.language, ignoreCase = true) &&
                (v.locale.country.equals("IN", ignoreCase = true) || v.name.contains("-in-", ignoreCase = true) || v.name.contains("_in_", ignoreCase = true))
            }
            if (targetVoice != null) {
                localTts?.voice = targetVoice
            }
        } catch (_: Exception) {
            // Ignore voice selection failure on engines without explicit voice query support
        }

        _currentlySpeakingId.value = id
        localTts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, id)
    }

    fun stop() {
        currentTtsJob?.cancel()
        currentTtsJob = null
        edgeTtsClient.stop()
        localTts?.stop()
        _currentlySpeakingId.value = null
        pendingCompletions.values.forEach { it.complete(Unit) }
        pendingCompletions.clear()
    }
}
