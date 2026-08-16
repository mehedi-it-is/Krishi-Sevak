package com.krishisevak.app.ui.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishisevak.app.data.engine.CropDoctorEngine
import com.krishisevak.app.data.engine.CropDoctorInput
import com.krishisevak.app.data.engine.CropDoctorResult
import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.data.local.db.PlantScanDao
import com.krishisevak.app.data.remote.kindwise.KindwiseApi
import com.krishisevak.app.data.remote.kindwise.KindwiseHealthRequest
import com.krishisevak.app.utils.TtsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import com.krishisevak.app.BuildConfig

class CropDoctorViewModel(
    private val plantScanDao: PlantScanDao,
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager,
    private val kindwiseApi: KindwiseApi,
    private val kindwiseApiKey: String = BuildConfig.KINDWISE_API_KEY
) : ViewModel() {

    val userLanguageCode: StateFlow<String> = dataStoreManager.userLanguageCodeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    private val _doctorResult = MutableStateFlow<CropDoctorResult?>(null)
    val doctorResult: StateFlow<CropDoctorResult?> = _doctorResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun analyzeImage(base64Image: String) {
        viewModelScope.launch {
            _isLoading.value = true
            
            val pastScans = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                plantScanDao.getRecentScans(10)
            }
            
            // Try to get disease from API
            var diseaseName = "Unknown"
            try {
                val request = KindwiseHealthRequest(images = listOf("data:image/jpeg;base64,$base64Image"))
                val response = kindwiseApi.analyzeCropHealth(kindwiseApiKey, request)
                val topSuggestion = response.result?.disease?.suggestions?.firstOrNull()
                if (topSuggestion != null) {
                    diseaseName = topSuggestion.name ?: "Pest / Fungal Infection"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Using CropDoctorEngine with the diagnosed disease context for structured report
            val input = CropDoctorInput(
                temperature = 31f,
                humidity = 78,
                rainProbability = 60,
                conditionDescription = if (diseaseName != "Unknown") "Diagnosed: $diseaseName" else "Partly Cloudy",
                pastScans = pastScans
            )

            val lang = userLanguageCode.value
            val result = CropDoctorEngine.evaluate(input, lang)
            _doctorResult.value = result

            // Save to Room DB
            if (diseaseName != "Unknown") {
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    val scan = com.krishisevak.app.data.local.db.PlantScanEntity(
                        plantName = "Crop",
                        disease = diseaseName,
                        isHealthy = diseaseName.contains("Healthy", ignoreCase = true) || diseaseName.contains("None", ignoreCase = true),
                        probability = 0.9f,
                        treatment = result.recommendations.firstOrNull() ?: "",
                        imageUri = null
                    )
                    plantScanDao.insertScan(scan)
                }
            }

            _isLoading.value = false
        }
    }

    fun toggleTts(id: String, text: String) {
        viewModelScope.launch {
            ttsManager.speak(id, text, userLanguageCode.value)
        }
    }
}
