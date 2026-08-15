package com.kisaandost.app.ui.soil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisaandost.app.data.engine.FertilizerAcreageResult
import com.kisaandost.app.data.engine.SoilAdvisoryEngine
import com.kisaandost.app.data.engine.SoilAnalysisInput
import com.kisaandost.app.data.engine.SoilAnalysisResult
import com.kisaandost.app.data.local.datastore.DataStoreManager
import com.kisaandost.app.utils.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class SoilScreenMode {
    DIAGNOSIS, CALCULATOR
}

class SoilViewModel(
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager
) : ViewModel() {

    private val _screenMode = MutableStateFlow(SoilScreenMode.CALCULATOR)
    val screenMode: StateFlow<SoilScreenMode> = _screenMode.asStateFlow()

    // Mode 1: Soil Test Params
    private val _soilType = MutableStateFlow("Alluvial Soil")
    val soilType: StateFlow<String> = _soilType.asStateFlow()

    private val _phInput = MutableStateFlow("6.8")
    val phInput: StateFlow<String> = _phInput.asStateFlow()

    private val _nitrogenInput = MutableStateFlow("140")
    val nitrogenInput: StateFlow<String> = _nitrogenInput.asStateFlow()

    private val _phosphorusInput = MutableStateFlow("24")
    val phosphorusInput: StateFlow<String> = _phosphorusInput.asStateFlow()

    private val _potassiumInput = MutableStateFlow("180")
    val potassiumInput: StateFlow<String> = _potassiumInput.asStateFlow()

    private val _analysisResult = MutableStateFlow<SoilAnalysisResult?>(null)
    val analysisResult: StateFlow<SoilAnalysisResult?> = _analysisResult.asStateFlow()

    // Mode 2: Fertilizer Dosage Calculator Params
    val cropsList = listOf("Wheat", "Rice", "Cotton", "Sugarcane", "Maize", "Potato", "Tomato", "Mustard", "Soyabean", "Gram")
    val unitsList = listOf("Acres", "Hectares", "Bigha")

    private val _calcCrop = MutableStateFlow("Wheat")
    val calcCrop: StateFlow<String> = _calcCrop.asStateFlow()

    private val _calcAcreage = MutableStateFlow("2.0")
    val calcAcreage: StateFlow<String> = _calcAcreage.asStateFlow()

    private val _calcUnit = MutableStateFlow("Acres")
    val calcUnit: StateFlow<String> = _calcUnit.asStateFlow()

    private val _calcSoilType = MutableStateFlow("Alluvial Soil")
    val calcSoilType: StateFlow<String> = _calcSoilType.asStateFlow()

    private val _fertilizerResult = MutableStateFlow<FertilizerAcreageResult?>(null)
    val fertilizerResult: StateFlow<FertilizerAcreageResult?> = _fertilizerResult.asStateFlow()

    val soilTypesList = listOf(
        "Alluvial Soil",
        "Black Cotton Soil",
        "Red Soil",
        "Laterite Soil",
        "Sandy Loam",
        "Clayey Soil"
    )

    val userLanguageCode = dataStoreManager.userLanguageCodeFlow

    init {
        viewModelScope.launch {
            userLanguageCode.collect {
                calculateAdvisory()
                calculateDosage()
            }
        }
    }

    fun setScreenMode(mode: SoilScreenMode) {
        _screenMode.value = mode
    }

    fun setSoilType(type: String) {
        _soilType.value = type
        calculateAdvisory()
    }

    fun setPh(v: String) {
        _phInput.value = v
    }

    fun setNitrogen(v: String) {
        _nitrogenInput.value = v
    }

    fun setPhosphorus(v: String) {
        _phosphorusInput.value = v
    }

    fun setPotassium(v: String) {
        _potassiumInput.value = v
    }

    fun setCalcCrop(crop: String) {
        _calcCrop.value = crop
        calculateDosage()
    }

    fun setCalcAcreage(acreage: String) {
        _calcAcreage.value = acreage
        calculateDosage()
    }

    fun setCalcUnit(unit: String) {
        _calcUnit.value = unit
        calculateDosage()
    }

    fun setCalcSoilType(soil: String) {
        _calcSoilType.value = soil
        calculateDosage()
    }

    fun calculateAdvisory() {
        val ph = _phInput.value.toFloatOrNull() ?: 6.8f
        val n = _nitrogenInput.value.toFloatOrNull() ?: 140f
        val p = _phosphorusInput.value.toFloatOrNull() ?: 24f
        val k = _potassiumInput.value.toFloatOrNull() ?: 180f

        val input = SoilAnalysisInput(
            soilType = _soilType.value,
            ph = ph,
            nitrogenPpm = n,
            phosphorusPpm = p,
            potassiumPpm = k
        )
        viewModelScope.launch {
            val lang = dataStoreManager.userLanguageCodeFlow.first()
            _analysisResult.value = SoilAdvisoryEngine.analyzeSoil(input, lang)
        }
    }

    fun calculateDosage() {
        val acreage = _calcAcreage.value.toFloatOrNull() ?: 1.0f
        viewModelScope.launch {
            val lang = dataStoreManager.userLanguageCodeFlow.first()
            _fertilizerResult.value = SoilAdvisoryEngine.calculateFertilizerAcreage(
                cropName = _calcCrop.value,
                acreage = acreage,
                unit = _calcUnit.value,
                soilType = _calcSoilType.value,
                langCode = lang
            )
        }
    }

    fun toggleTts(tag: String, text: String) {
        viewModelScope.launch {
            if (ttsManager.currentlySpeakingId.value == tag) {
                ttsManager.stop()
            } else {
                val lang = dataStoreManager.userLanguageCodeFlow.first()
                ttsManager.speak(tag, text, lang)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }
}
