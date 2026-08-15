package com.kisaandost.app.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kisaandost_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val USER_UUID = stringPreferencesKey("user_uuid")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_LANGUAGE_CODE = stringPreferencesKey("user_language_code")
        val USER_LANGUAGE_NAME = stringPreferencesKey("user_language_name")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")

        // Location preferences
        val LOCATION_CITY = stringPreferencesKey("loc_city")
        val LOCATION_DISTRICT = stringPreferencesKey("loc_district")
        val LOCATION_STATE = stringPreferencesKey("loc_state")
        val LOCATION_LAT = doublePreferencesKey("loc_lat")
        val LOCATION_LON = doublePreferencesKey("loc_lon")
        
        val LAST_SCHEMES_FETCH_TIME = androidx.datastore.preferences.core.longPreferencesKey("last_schemes_fetch_time")
    }

    val userUuidFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_UUID] ?: ""
    }

    val userNameFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME] ?: ""
    }

    val userLanguageCodeFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_LANGUAGE_CODE] ?: "hi"
    }

    val userLanguageNameFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_LANGUAGE_NAME] ?: "Hindi"
    }

    val isDarkModeFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_DARK_MODE] ?: true
    }

    val locationCityFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LOCATION_CITY] ?: "Nashik"
    }

    val locationDistrictFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LOCATION_DISTRICT] ?: "Nashik"
    }

    val locationStateFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LOCATION_STATE] ?: "Maharashtra"
    }

    val locationLatFlow: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LOCATION_LAT] ?: 19.9975
    }

    val locationLonFlow: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LOCATION_LON] ?: 73.7898
    }

    suspend fun saveUserOnboarding(name: String, languageCode: String, languageName: String) {
        context.dataStore.edit { prefs ->
            if (prefs[USER_UUID].isNullOrEmpty()) {
                prefs[USER_UUID] = UUID.randomUUID().toString()
            }
            prefs[USER_NAME] = name
            prefs[USER_LANGUAGE_CODE] = languageCode
            prefs[USER_LANGUAGE_NAME] = languageName
            if (prefs[IS_DARK_MODE] == null) {
                prefs[IS_DARK_MODE] = true
            }
        }
    }

    suspend fun updateLocation(city: String, district: String, state: String, lat: Double, lon: Double) {
        context.dataStore.edit { prefs ->
            prefs[LOCATION_CITY] = city
            prefs[LOCATION_DISTRICT] = district
            prefs[LOCATION_STATE] = state
            prefs[LOCATION_LAT] = lat
            prefs[LOCATION_LON] = lon
        }
    }

    suspend fun updateLanguage(languageCode: String, languageName: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_LANGUAGE_CODE] = languageCode
            prefs[USER_LANGUAGE_NAME] = languageName
        }
    }

    suspend fun updateUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = name
        }
    }

    val lastSchemesFetchTimeFlow: Flow<Long> = context.dataStore.data.map { prefs ->
        prefs[LAST_SCHEMES_FETCH_TIME] ?: 0L
    }

    suspend fun updateLastSchemesFetchTime(time: Long) {
        context.dataStore.edit { prefs ->
            prefs[LAST_SCHEMES_FETCH_TIME] = time
        }
    }

    suspend fun setDarkMode(isDark: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_DARK_MODE] = isDark
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
