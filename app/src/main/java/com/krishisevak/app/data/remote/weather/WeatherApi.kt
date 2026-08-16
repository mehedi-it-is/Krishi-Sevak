package com.krishisevak.app.data.remote.weather

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(
    @SerializedName("main") val main: MainWeatherData?,
    @SerializedName("weather") val weather: List<WeatherDescription>?,
    @SerializedName("name") val cityName: String?,
    @SerializedName("wind") val wind: WindData?
)

data class MainWeatherData(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("temp_min") val tempMin: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("feels_like") val feelsLike: Double?
)

data class WeatherDescription(
    @SerializedName("main") val mainCondition: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)

data class WindData(
    @SerializedName("speed") val speed: Double?
)

// 5-Day Forecast Data Models
data class ForecastResponse(
    @SerializedName("list") val forecastList: List<ForecastItem>?,
    @SerializedName("city") val city: ForecastCity?
)

data class ForecastItem(
    @SerializedName("dt") val dt: Long,
    @SerializedName("dt_txt") val dtTxt: String?,
    @SerializedName("main") val main: MainWeatherData?,
    @SerializedName("weather") val weather: List<WeatherDescription>?,
    @SerializedName("pop") val pop: Double? // Probability of precipitation (0.0 to 1.0)
)

data class ForecastCity(
    @SerializedName("name") val name: String?,
    @SerializedName("country") val country: String?
)

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("data/2.5/forecast")
    suspend fun get5DayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}
