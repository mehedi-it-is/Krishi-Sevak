package com.krishisevak.app.data.remote.tts

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.*
import okio.ByteString
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

class EdgeTtsClient(private val context: Context) {

    companion object {
        private const val TAG = "EdgeTtsClient"
        private const val WSS_URL = "wss://speech.platform.bing.com/consumer/speech/synthesize/readaloud/edge/v1?TrustedClientToken=6A5AA1D4EAFA4CC0BE69AACFE1460C06&ConnectionId="

        val VOICE_MAP = mapOf(
            "en" to "en-IN-NeerjaNeural",
            "hi" to "hi-IN-SwaraNeural",
            "bn" to "bn-IN-TanishaaNeural",
            "mr" to "mr-IN-AarohiNeural",
            "te" to "te-IN-ShrutiNeural",
            "ta" to "ta-IN-PallaviNeural",
            "kn" to "kn-IN-SapnaNeural",
            "ml" to "ml-IN-SobhanaNeural",
            "gu" to "gu-IN-DhwaniNeural",
            "pa" to "pa-IN-OjasNeural",
            "or" to "or-IN-SubhasiniNeural"
        )

        val LOCALE_MAP = mapOf(
            "en" to "en-IN",
            "hi" to "hi-IN",
            "bn" to "bn-IN",
            "mr" to "mr-IN",
            "te" to "te-IN",
            "ta" to "ta-IN",
            "kn" to "kn-IN",
            "ml" to "ml-IN",
            "gu" to "gu-IN",
            "pa" to "pa-IN",
            "or" to "or-IN"
        )

        private const val MIN_AUDIO_BYTES = 512
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .pingInterval(20, TimeUnit.SECONDS)
        .build()

    private var mediaPlayer: MediaPlayer? = null

    fun getVoiceName(langCode: String): String {
        return VOICE_MAP[langCode.lowercase()] ?: "hi-IN-SwaraNeural"
    }

    fun getVoiceLocale(langCode: String): String {
        return LOCALE_MAP[langCode.lowercase()] ?: "hi-IN"
    }

    suspend fun synthesizeToAudioFile(text: String, langCode: String): File? = withContext(Dispatchers.IO) {
        val voiceName = getVoiceName(langCode)
        val voiceLocale = getVoiceLocale(langCode)
        val connectionId = UUID.randomUUID().toString().replace("-", "")
        val requestId = UUID.randomUUID().toString().replace("-", "")

        Log.d(TAG, "Starting Edge TTS synthesis: lang=$langCode, voice=$voiceName, textLen=${text.length}")

        val audioStream = ByteArrayOutputStream()
        val completionDeferred = CompletableDeferred<Boolean>()

        val fullUrl = WSS_URL + connectionId

        val request = Request.Builder()
            .url(fullUrl)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36 Edg/130.0.0.0")
            .header("Origin", "chrome-extension://jdiccldimpdaibmpdkjnbmckianbfold")
            .header("Accept-Encoding", "gzip, deflate, br")
            .header("Accept-Language", "en-US,en;q=0.9")
            .build()

        val webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket connected successfully. Sending config...")

                val configMsg = "Content-Type:application/json; charset=utf-8\r\n" +
                        "Path:speech.config\r\n\r\n" +
                        "{\"context\":{\"synthesis\":{\"audio\":{\"metadataoptions\":{" +
                        "\"sentenceBoundaryEnabled\":\"false\"," +
                        "\"wordBoundaryEnabled\":\"false\"}," +
                        "\"outputFormat\":\"audio-24khz-48kbitrate-mono-mp3\"}}}}"
                webSocket.send(configMsg)

                val cleanText = text
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;")

                val ssmlMsg = "X-RequestId:$requestId\r\n" +
                        "Content-Type:application/ssml+xml\r\n" +
                        "Path:ssml\r\n\r\n" +
                        "<speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xml:lang='$voiceLocale'>" +
                        "<voice name='$voiceName'>" +
                        "<prosody pitch='+0Hz' rate='+0%' volume='+0%'>$cleanText</prosody>" +
                        "</voice></speak>"

                Log.d(TAG, "Sent SSML request for voice=$voiceName")
                webSocket.send(ssmlMsg)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                val data = bytes.toByteArray()
                if (data.size >= 2) {
                    val headerLength = ((data[0].toInt() and 0xFF) shl 8) or (data[1].toInt() and 0xFF)
                    val audioOffset = 2 + headerLength
                    if (data.size > audioOffset) {
                        audioStream.write(data, audioOffset, data.size - audioOffset)
                    }
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                if (text.contains("Path:turn.end")) {
                    Log.d(TAG, "Received turn.end. Total audio bytes: ${audioStream.size()}")
                    webSocket.close(1000, "Done")
                    completionDeferred.complete(true)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "Edge TTS WebSocket FAILURE: ${t.javaClass.simpleName}: ${t.message}", t)
                if (!completionDeferred.isCompleted) {
                    completionDeferred.complete(false)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closing: code=$code, reason=$reason")
                webSocket.close(code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closed: code=$code, reason=$reason")
                if (!completionDeferred.isCompleted) {
                    completionDeferred.complete(audioStream.size() > MIN_AUDIO_BYTES)
                }
            }
        })

        try {
            val success = withTimeoutOrNull(25_000) {
                completionDeferred.await()
            } ?: false

            if (success && audioStream.size() > MIN_AUDIO_BYTES) {
                val cacheDir = File(context.cacheDir, "edge_tts")
                cacheDir.mkdirs()
                cacheDir.listFiles()?.sortedByDescending { it.lastModified() }?.drop(5)?.forEach { it.delete() }

                val audioFile = File(cacheDir, "edge_tts_${System.currentTimeMillis()}.mp3")
                FileOutputStream(audioFile).use { fos ->
                    fos.write(audioStream.toByteArray())
                }
                Log.d(TAG, "Edge TTS audio saved: ${audioFile.name}, size=${audioFile.length()} bytes")
                return@withContext audioFile
            } else {
                Log.w(TAG, "Edge TTS failed: success=$success, audioSize=${audioStream.size()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Edge TTS synthesis exception: ${e.javaClass.simpleName}: ${e.message}", e)
        }
        return@withContext null
    }

    suspend fun playAudio(
        file: File,
        onStart: () -> Unit,
        onCompletion: () -> Unit
    ) = withContext(Dispatchers.Main) {
        stop()
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                        .build()
                )
                setDataSource(file.absolutePath)
                prepare()
                setOnCompletionListener {
                    onCompletion()
                    releasePlayer()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                    onCompletion()
                    releasePlayer()
                    true
                }
                start()
                onStart()
                Log.d(TAG, "Edge TTS playback started: ${file.name}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing Edge TTS audio file: ${e.message}", e)
            onCompletion()
        }
    }

    private fun releasePlayer() {
        try {
            mediaPlayer?.release()
        } catch (e: Exception) {
            Log.w(TAG, "Error releasing MediaPlayer: ${e.message}")
        } finally {
            mediaPlayer = null
        }
    }

    fun stop() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error stopping MediaPlayer: ${e.message}")
        } finally {
            mediaPlayer = null
        }
    }
}
