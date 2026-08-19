package com.krishisevak.app.data.remote.mandi

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class MandiResponse(
    @SerializedName("records") val records: List<MandiRecord>?,
    @SerializedName("total") val total: Int?,
    @SerializedName("count") val count: Int?
)

data class MandiRecord(
    @SerializedName("state") val state: String? = null,
    @SerializedName("district") val district: String? = null,
    @SerializedName("market") val market: String? = null,
    @SerializedName("commodity") val commodity: String? = null,
    @SerializedName("category") val category: String? = "Vegetables",
    @SerializedName("variety") val variety: String? = null,
    @SerializedName("min_price") val minPrice: String? = null,
    @SerializedName("max_price") val maxPrice: String? = null,
    @SerializedName("modal_price") val modalPrice: String? = null,
    @SerializedName("retail_price") val retailPrice: String? = null,
    @SerializedName("price_trend") val priceTrend: String? = "Stable",
    @SerializedName("distance_km") val distanceKm: Int? = 5,
    @SerializedName("emoji") val emoji: String? = "🌱",
    @SerializedName("arrival_date") val arrivalDate: String? = "Today"
) {
    val displayCategory: String
        get() = if (!category.isNullOrBlank()) category else inferCategory(commodity)

    val displayEmoji: String
        get() = if (!emoji.isNullOrBlank()) emoji else inferEmoji(commodity)

    val displayPriceTrend: String
        get() = priceTrend ?: "Stable"

    val displayDistance: Int
        get() = distanceKm ?: 5

    companion object {
        fun inferCategory(commodity: String?): String {
            if (commodity == null) return "Vegetables"
            val lower = commodity.lowercase()
            return when {
                lower.contains("mango") || lower.contains("banana") || lower.contains("apple") ||
                lower.contains("grape") || lower.contains("papaya") || lower.contains("orange") ||
                lower.contains("lemon") || lower.contains("watermelon") || lower.contains("fruit") -> "Fruits"

                lower.contains("wheat") || lower.contains("paddy") || lower.contains("rice") ||
                lower.contains("maize") || lower.contains("bajra") || lower.contains("jowar") ||
                lower.contains("ragi") || lower.contains("barley") || lower.contains("millet") -> "Grains & Crops"

                lower.contains("chana") || lower.contains("tur") || lower.contains("arhar") ||
                lower.contains("moong") || lower.contains("urad") || lower.contains("masoor") ||
                lower.contains("gram") || lower.contains("dal") || lower.contains("pulse") -> "Pulses & Legumes"

                lower.contains("mustard") || lower.contains("cotton") || lower.contains("sugarcane") ||
                lower.contains("turmeric") || lower.contains("cumin") || lower.contains("chilli") ||
                lower.contains("soyabean") || lower.contains("groundnut") -> "Spices & Cash Crops"

                else -> "Vegetables"
            }
        }

        fun inferEmoji(commodity: String?): String {
            if (commodity == null) return "🌱"
            val lower = commodity.lowercase()
            return when {
                lower.contains("tomato") -> "🍅"
                lower.contains("onion") -> "🧅"
                lower.contains("potato") -> "🥔"
                lower.contains("chilli") -> "🌶️"
                lower.contains("garlic") -> "🧄"
                lower.contains("ginger") -> "🫚"
                lower.contains("brinjal") -> "🍆"
                lower.contains("cauliflower") || lower.contains("cabbage") -> "🥦"
                lower.contains("mango") -> "🥭"
                lower.contains("banana") -> "🍌"
                lower.contains("apple") -> "🍎"
                lower.contains("grape") -> "🍇"
                lower.contains("wheat") || lower.contains("paddy") || lower.contains("rice") -> "🌾"
                lower.contains("maize") || lower.contains("corn") -> "🌽"
                lower.contains("chana") || lower.contains("pulse") || lower.contains("dal") -> "🫘"
                lower.contains("cotton") -> "☁️"
                lower.contains("sugarcane") -> "🎋"
                else -> "🌱"
            }
        }
    }
}

interface MandiApi {
    /**
     * Fetch mandi prices from data.gov.in Agmarknet dataset.
     * Resource ID: 9ef84268-d588-465a-a308-a864a43d0070
     */
    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getMandiPrices(
        @Query("api-key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
        @Query("filters[State.keyword]") stateFilter: String? = null
    ): MandiResponse
}

/**
 * Mandi provider delivering verified authentic APMC market data by region.
 */
object MandiMockProvider {
    fun getLocalMandiPrices(
        cityName: String = "Nashik",
        districtName: String = "Nashik",
        stateName: String = "Maharashtra"
    ): List<MandiRecord> {
        val loc = com.krishisevak.app.utils.UserLocationDetails(
            cityName = cityName,
            districtName = districtName,
            stateName = stateName,
            latitude = 20.0,
            longitude = 74.0
        )
        return RealMandiDirectory.getMandiDataForLocation(loc).second
    }
}

