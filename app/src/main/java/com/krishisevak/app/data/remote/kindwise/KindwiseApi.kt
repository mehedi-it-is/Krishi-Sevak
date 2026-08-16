package com.krishisevak.app.data.remote.kindwise

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class KindwiseHealthRequest(
    @SerializedName("images") val images: List<String>,
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null,
    @SerializedName("similar_images") val similarImages: Boolean = true
)

data class KindwiseHealthResponse(
    @SerializedName("result") val result: KindwiseResult?
)

data class KindwiseResult(
    @SerializedName("disease") val disease: KindwiseDiseaseDetails?
)

data class KindwiseDiseaseDetails(
    @SerializedName("suggestions") val suggestions: List<KindwiseSuggestion>?
)

data class KindwiseSuggestion(
    @SerializedName("name") val name: String,
    @SerializedName("probability") val probability: Double,
    @SerializedName("details") val details: KindwiseSuggestionDetails?
)

data class KindwiseSuggestionDetails(
    @SerializedName("description") val description: String?
)

interface KindwiseApi {
    @POST("api/v1/identification")
    suspend fun analyzeCropHealth(
        @Header("Api-Key") apiKey: String,
        @Body request: KindwiseHealthRequest
    ): KindwiseHealthResponse
}
