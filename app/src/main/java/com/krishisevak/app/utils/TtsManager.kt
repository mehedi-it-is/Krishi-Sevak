package com.krishisevak.app.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.krishisevak.app.data.remote.tts.EdgeTtsClient
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class TtsManager(private val context: Context) : TextToSpeech.OnInitListener {

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

    /**
     * Fire-and-forget Neural TTS. Toggles pause if clicking the same item.
     * Uses Microsoft Edge Neural TTS with instant fallback to on-device TTS.
     */
    fun speak(id: String, text: String, languageCode: String) {
        if (_currentlySpeakingId.value == id) {
            stop()
            return
        }

        stop()

        currentTtsJob = coroutineScope.launch {
            _currentlySpeakingId.value = id
            Log.d(TAG, "Attempting Edge Neural TTS for id=$id, lang=$languageCode")

            if (!isNetworkAvailable()) {
                Log.d(TAG, "No internet connection — immediately falling back to local TTS for id=$id, lang=$languageCode")
                speakWithLocalEngine(id, text, languageCode)
                return@launch
            }

            // Attempt Edge Neural TTS first
            val audioFile = try {
                withTimeout(25_000) {
                    edgeTtsClient.synthesizeToAudioFile(text, languageCode)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Edge TTS attempt failed: ${e.javaClass.simpleName}: ${e.message}")
                null
            }

            if (audioFile != null && isActive) {
                Log.d(TAG, "Edge TTS SUCCESS — playing audio for id=$id")
                edgeTtsClient.playAudio(
                    file = audioFile,
                    onStart = { _currentlySpeakingId.value = id },
                    onCompletion = {
                        if (_currentlySpeakingId.value == id) {
                            _currentlySpeakingId.value = null
                        }
                    }
                )
            } else if (isActive) {
                // Fallback to local on-device TTS
                Log.d(TAG, "Edge TTS failed or returned null — falling back to local TTS for id=$id, lang=$languageCode")
                speakWithLocalEngine(id, text, languageCode)
            }
        }
    }

    /**
     * Suspending TTS for onboarding / sequential line-by-line speech.
     */
    suspend fun speakAndWait(id: String, text: String, languageCode: String) {
        speakMutex.withLock {
            val deferred = CompletableDeferred<Unit>()
            pendingCompletions[id] = deferred

            if (!isNetworkAvailable()) {
                Log.d(TAG, "No internet connection — immediately falling back to local TTS for id=$id")
                speakWithLocalEngine(id, text, languageCode)
                try {
                    withTimeout(30000) {
                        deferred.await()
                    }
                } catch (e: TimeoutCancellationException) {
                    Log.w(TAG, "speakAndWait timed out for id=$id")
                    pendingCompletions.remove(id)
                }
                return@withLock
            }

            val audioFile = try {
                withTimeout(25_000) {
                    edgeTtsClient.synthesizeToAudioFile(text, languageCode)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Edge TTS speakAndWait failed: ${e.message}")
                null
            }

            if (audioFile != null) {
                Log.d(TAG, "Edge TTS speakAndWait SUCCESS for id=$id")
                edgeTtsClient.playAudio(
                    file = audioFile,
                    onStart = { _currentlySpeakingId.value = id },
                    onCompletion = {
                        if (_currentlySpeakingId.value == id) {
                            _currentlySpeakingId.value = null
                        }
                        pendingCompletions.remove(id)?.complete(Unit)
                    }
                )
            } else {
                Log.d(TAG, "Edge TTS speakAndWait failed — falling back to local TTS for id=$id")
                speakWithLocalEngine(id, text, languageCode)
            }

            try {
                withTimeout(30000) {
                    deferred.await()
                }
            } catch (e: TimeoutCancellationException) {
                Log.w(TAG, "speakAndWait timed out for id=$id")
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
