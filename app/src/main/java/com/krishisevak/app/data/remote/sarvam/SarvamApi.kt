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
    @SerializedName("model") val model: String = "sarvam-2b",
    @SerializedName("messages") val messages: List<SarvamChatMessage>,
    @SerializedName("temperature") val temperature: Double = 0.7
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
}
