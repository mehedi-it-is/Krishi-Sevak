package com.krishisevak.app.data.remote.sarvam

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class SarvamChatMessage(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: String
)

data class SarvamChatRequest(
    @SerializedName("model") val model: String = "sarvam-105b-conversations",
    @SerializedName("messages") val messages: List<SarvamChatMessage>,
    @SerializedName("temperature") val temperature: Double = 0.3,
    @SerializedName("max_tokens") val maxTokens: Int = 1024
)

data class SarvamChatResponse(
    @SerializedName("choices") val choices: List<SarvamChoice>?
)

data class SarvamChoice(
    @SerializedName("message") val message: SarvamChatMessage?
)

data class SarvamSttResponse(
    @SerializedName("transcript") val transcript: String?,
    @SerializedName("language_code") val languageCode: String?
)

data class SarvamTtsRequest(
    @SerializedName("inputs") val inputs: List<String>,
    @SerializedName("target_language_code") val targetLanguageCode: String = "hi-IN",
    @SerializedName("speaker") val speaker: String? = "ritu",
    @SerializedName("pitch") val pitch: Double? = 0.0,
    @SerializedName("pace") val pace: Double? = 1.0,
    @SerializedName("loudness") val loudness: Double? = 1.2,
    @SerializedName("speech_sample_rate") val speechSampleRate: Int? = 16000,
    @SerializedName("enable_preprocessing") val enablePreprocessing: Boolean = true,
    @SerializedName("model") val model: String = "bulbul:v3"
)

data class SarvamTtsResponse(
    @SerializedName("audios") val audios: List<String>?
)

interface SarvamApi {
    @POST("v1/chat/completions")
    suspend fun generateAdvisory(
        @Header("api-subscription-key") apiKey: String,
        @Body request: SarvamChatRequest
    ): SarvamChatResponse

    @Multipart
    @POST("speech-to-text")
    suspend fun speechToText(
        @Header("api-subscription-key") apiKey: String,
        @Part file: MultipartBody.Part,
        @Part("model") model: RequestBody,
        @Part("language_code") languageCode: RequestBody? = null
    ): SarvamSttResponse

    @POST("text-to-speech")
    suspend fun textToSpeech(
        @Header("api-subscription-key") apiKey: String,
        @Body request: SarvamTtsRequest
    ): SarvamTtsResponse
}
