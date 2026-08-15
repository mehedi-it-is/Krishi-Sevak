package com.kisaandost.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale

data class UserLocationDetails(
    val cityName: String,
    val districtName: String,
    val stateName: String,
    val latitude: Double,
    val longitude: Double,
    val isManualSelection: Boolean = false
)

class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    companion object {
        val PRESET_LOCATIONS = listOf(
            UserLocationDetails("Nashik", "Nashik", "Maharashtra", 19.9975, 73.7898, true),
            UserLocationDetails("Pune", "Pune", "Maharashtra", 18.5204, 73.8567, true),
            UserLocationDetails("Ludhiana", "Ludhiana", "Punjab", 30.9010, 75.8573, true),
            UserLocationDetails("Indore", "Indore", "Madhya Pradesh", 22.7196, 75.8577, true),
            UserLocationDetails("Warangal", "Warangal", "Telangana", 17.9689, 79.5941, true),
            UserLocationDetails("Kanpur", "Kanpur", "Uttar Pradesh", 26.4499, 80.3319, true),
            UserLocationDetails("Karnal", "Karnal", "Haryana", 29.6857, 76.9905, true),
            UserLocationDetails("Guntur", "Guntur", "Andhra Pradesh", 16.3067, 80.4365, true),
            UserLocationDetails("Patna", "Patna", "Bihar", 25.5941, 85.1376, true),
            UserLocationDetails("Jaipur", "Jaipur", "Rajasthan", 26.9124, 75.7873, true),
            UserLocationDetails("Ahmedabad", "Ahmedabad", "Gujarat", 23.0225, 72.5714, true),
            UserLocationDetails("Coimbatore", "Coimbatore", "Tamil Nadu", 11.0168, 76.9558, true),
            UserLocationDetails("Mysuru", "Mysuru", "Karnataka", 12.2958, 76.6394, true),
            UserLocationDetails("Kolkata", "Kolkata", "West Bengal", 22.5726, 88.3639, true)
        )
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return try {
            val cancellationTokenSource = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                cancellationTokenSource.token
            ).await() ?: fusedLocationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Resolves Lat/Lon coordinates into City, District, and State names using Geocoder with robust fallbacks
     */
    suspend fun resolveLocationDetails(lat: Double, lon: Double): UserLocationDetails = withContext(Dispatchers.IO) {
        try {
            if (Geocoder.isPresent()) {
                val geocoder = Geocoder(context, Locale.getDefault())
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lon, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address: Address = addresses[0]
                    val city = address.locality ?: address.subAdminArea ?: address.subLocality ?: "Nashik"
                    val district = address.subAdminArea ?: address.locality ?: city
                    val state = address.adminArea ?: "Maharashtra"

                    return@withContext UserLocationDetails(
                        cityName = city,
                        districtName = district,
                        stateName = state,
                        latitude = lat,
                        longitude = lon,
                        isManualSelection = false
                    )
                }
            }
        } catch (e: Exception) {
            // Geocoder failed or no network on emulator; fallback to closest preset
        }

        // Fallback: Find closest preset location by coordinate distance
        val closestPreset = PRESET_LOCATIONS.minByOrNull { preset ->
            val dLat = preset.latitude - lat
            val dLon = preset.longitude - lon
            (dLat * dLat) + (dLon * dLon)
        } ?: PRESET_LOCATIONS.first()

        return@withContext closestPreset.copy(latitude = lat, longitude = lon, isManualSelection = false)
    }
}
