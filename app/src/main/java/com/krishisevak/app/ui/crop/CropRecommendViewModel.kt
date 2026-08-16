package com.krishisevak.app.ui.crop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishisevak.app.data.engine.CropRecommendInput
import com.krishisevak.app.data.engine.CropRecommendationEngine
import com.krishisevak.app.data.engine.RecommendedCropItem
import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.utils.TtsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CropRecommendViewModel(
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager
) : ViewModel() {

    val userLanguageCode: StateFlow<String> = dataStoreManager.userLanguageCodeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    private val _selectedSeason = MutableStateFlow("Kharif (Monsoon)")
    val selectedSeason: StateFlow<String> = _selectedSeason.asStateFlow()

    private val _selectedWater = MutableStateFlow("Medium (Rainfed / Borewell)")
    val selectedWater: StateFlow<String> = _selectedWater.asStateFlow()

    private val _selectedSoil = MutableStateFlow("Alluvial / Loamy")
    val selectedSoil: StateFlow<String> = _selectedSoil.asStateFlow()

    private val _previousCrop = MutableStateFlow("Rice / Paddy")
    val previousCrop: StateFlow<String> = _previousCrop.asStateFlow()

    private val _recommendations = MutableStateFlow<List<RecommendedCropItem>>(emptyList())
    val recommendations: StateFlow<List<RecommendedCropItem>> = _recommendations.asStateFlow()

    val seasonsList = listOf("Kharif (Monsoon)", "Rabi (Winter)", "Zaid (Summer)")
    val waterList = listOf("High (Canal / Tube well)", "Medium (Rainfed / Borewell)", "Low (Dryland / Drip)")
    val soilList = listOf("Alluvial / Loamy", "Black Cotton", "Red / Laterite", "Clayey", "Sandy Loam")
    val previousCropList = listOf("Rice / Paddy", "Wheat", "Maize", "Cotton", "Fallow (None)")

    init {
        viewModelScope.launch {
            userLanguageCode.collect {
                generateRecommendations()
            }
        }
    }

    fun setSeason(season: String) {
        _selectedSeason.value = season
        generateRecommendations()
    }

    fun setWater(water: String) {
        _selectedWater.value = water
        generateRecommendations()
    }

    fun setSoil(soil: String) {
        _selectedSoil.value = soil
        generateRecommendations()
    }

    fun setPreviousCrop(crop: String) {
        _previousCrop.value = crop
        generateRecommendations()
    }

    fun generateRecommendations() {
        val input = CropRecommendInput(
            season = _selectedSeason.value,
            waterAvailability = _selectedWater.value,
            soilType = _selectedSoil.value,
            previousCrop = _previousCrop.value
        )
        val recs = CropRecommendationEngine.recommend(input, userLanguageCode.value)
        _recommendations.value = recs
    }

    fun toggleTts(id: String, text: String) {
        viewModelScope.launch {
            ttsManager.speak(id, text, userLanguageCode.value)
        }
    }
}
