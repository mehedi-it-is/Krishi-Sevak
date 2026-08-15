package com.kisaandost.app.utils

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File

class VoiceRecorderHelper(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var currentOutputFile: File? = null
    private var isRecording = false

    fun startRecording(): File? {
        try {
            val cacheDir = context.cacheDir
            val audioFile = File(cacheDir, "voice_query_${System.currentTimeMillis()}.m4a")
            currentOutputFile = audioFile

            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(audioFile.absolutePath)
                prepare()
                start()
            }

            mediaRecorder = recorder
            isRecording = true
            Log.d("VoiceRecorderHelper", "Recording started: ${audioFile.absolutePath}")
            return audioFile
        } catch (e: Exception) {
            Log.e("VoiceRecorderHelper", "Failed to start recording: ${e.message}", e)
            stopRecording()
            return null
        }
    }

    fun stopRecording(): File? {
        val file = currentOutputFile
        try {
            if (isRecording) {
                mediaRecorder?.apply {
                    stop()
                    release()
                }
            }
        } catch (e: Exception) {
            Log.e("VoiceRecorderHelper", "Error stopping recorder: ${e.message}")
        } finally {
            mediaRecorder = null
            isRecording = false
        }
        return if (file != null && file.exists() && file.length() > 0) file else null
    }

    fun cancelRecording() {
        try {
            if (isRecording) {
                mediaRecorder?.apply {
                    stop()
                    release()
                }
            }
            currentOutputFile?.delete()
        } catch (e: Exception) {
            Log.e("VoiceRecorderHelper", "Error canceling recorder: ${e.message}")
        } finally {
            mediaRecorder = null
            isRecording = false
            currentOutputFile = null
        }
    }

    fun isCurrentlyRecording(): Boolean = isRecording
}
