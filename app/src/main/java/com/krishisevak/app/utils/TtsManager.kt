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
        return when (languageCode.lowercase()) {
            "hi" -> Locale("hi", "IN")
            "bn" -> Locale("bn", "IN")
            "kn" -> Locale("kn", "IN")
            "ml" -> Locale("ml", "IN")
            "mr" -> Locale("mr", "IN")
            "or" -> Locale("or", "IN")
            "pa" -> Locale("pa", "IN")
            "ta" -> Locale("ta", "IN")
            "te" -> Locale("te", "IN")
            "gu" -> Locale("gu", "IN")
            else -> Locale.ENGLISH
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
     * Synthesize audio file with Sarvam AI bulbul:v3
     */
    private suspend fun synthesizeWithSarvamBulbul(text: String, languageCode: String): File? = withContext(Dispatchers.IO) {
        val api = sarvamApi ?: return@withContext null
        if (sarvamApiKey.isBlank() || sarvamApiKey == "DEMO_SARVAM_KEY") return@withContext null

        try {
            val sarvamLang = mapToSarvamLanguage(languageCode)
            val clean = cleanTextForSpeech(text).take(500)
            if (clean.isBlank()) return@withContext null

            val req = SarvamTtsRequest(
                inputs = listOf(clean),
                targetLanguageCode = sarvamLang,
                model = "bulbul:v3"
            )
            val response = api.textToSpeech(sarvamApiKey, req)
            val base64Audio = response.audios?.firstOrNull()
            if (!base64Audio.isNullOrBlank()) {
                val audioBytes = Base64.decode(base64Audio, Base64.DEFAULT)
                val tempFile = File(context.cacheDir, "sarvam_bulbul_${UUID.randomUUID()}.wav")
                FileOutputStream(tempFile).use { it.write(audioBytes) }
                return@withContext tempFile
            }
        } catch (e: Exception) {
            Log.w(TAG, "Sarvam bulbul:v3 TTS failed: ${e.message}")
        }
        return@withContext null
    }

    /**
     * Speaks aloud using Sarvam AI bulbul:v3 with instant fallback to Edge Neural and Local TTS.
     */
    fun speak(id: String, text: String, languageCode: String) {
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

            if (!isNetworkAvailable()) {
                Log.d(TAG, "No internet connection — falling back to local TTS for id=$id")
                speakWithLocalEngine(id, cleanText, languageCode)
                return@launch
            }

            // Step 1: Try Sarvam AI bulbul:v3
            val sarvamAudioFile = try {
                withTimeout(15_000) {
                    synthesizeWithSarvamBulbul(cleanText, languageCode)
                }
            } catch (e: Exception) {
                null
            }

            if (sarvamAudioFile != null && isActive) {
                Log.d(TAG, "Sarvam bulbul:v3 TTS SUCCESS — playing audio for id=$id")
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

            // Step 2: Fallback to Edge Neural TTS
            val edgeAudioFile = try {
                withTimeout(15_000) {
                    edgeTtsClient.synthesizeToAudioFile(cleanText, languageCode)
                }
            } catch (e: Exception) {
                null
            }

            if (edgeAudioFile != null && isActive) {
                Log.d(TAG, "Edge TTS SUCCESS — playing audio for id=$id")
                edgeTtsClient.playAudio(
                    file = edgeAudioFile,
                    onStart = { _currentlySpeakingId.value = id },
                    onCompletion = {
                        if (_currentlySpeakingId.value == id) {
                            _currentlySpeakingId.value = null
                        }
                    }
                )
            } else if (isActive) {
                // Step 3: Fallback to local on-device TTS
                Log.d(TAG, "Falling back to local TTS for id=$id, lang=$languageCode")
                speakWithLocalEngine(id, cleanText, languageCode)
            }
        }
    }

    suspend fun speakAndWait(id: String, text: String, languageCode: String) {
        speakMutex.withLock {
            val deferred = CompletableDeferred<Unit>()
            pendingCompletions[id] = deferred

            val cleanText = cleanTextForSpeech(text)

            if (!isNetworkAvailable()) {
                speakWithLocalEngine(id, cleanText, languageCode)
                try {
                    withTimeout(30000) { deferred.await() }
                } catch (e: TimeoutCancellationException) {
                    pendingCompletions.remove(id)
                }
                return@withLock
            }

            val sarvamAudioFile = try {
                withTimeout(15_000) {
                    synthesizeWithSarvamBulbul(cleanText, languageCode)
                }
            } catch (e: Exception) {
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
                val edgeAudioFile = try {
                    withTimeout(15_000) {
                        edgeTtsClient.synthesizeToAudioFile(cleanText, languageCode)
                    }
                } catch (e: Exception) {
                    null
                }

                if (edgeAudioFile != null) {
                    edgeTtsClient.playAudio(
                        file = edgeAudioFile,
                        onStart = { _currentlySpeakingId.value = id },
                        onCompletion = {
                            if (_currentlySpeakingId.value == id) {
                                _currentlySpeakingId.value = null
                            }
                            pendingCompletions.remove(id)?.complete(Unit)
                        }
                    )
                } else {
                    speakWithLocalEngine(id, cleanText, languageCode)
                }
            }

            try {
                withTimeout(30000) { deferred.await() }
            } catch (e: TimeoutCancellationException) {
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
            Log.w(TAG, "Local TTS language $languageCode not supported, falling back to English")
            localTts?.language = Locale.ENGLISH
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
