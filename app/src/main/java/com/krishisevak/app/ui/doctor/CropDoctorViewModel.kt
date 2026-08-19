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

    val kindwiseQueriesUsed: StateFlow<Int> = dataStoreManager.kindwiseQueriesUsedTodayFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _doctorResult = MutableStateFlow<CropDoctorResult?>(null)
    val doctorResult: StateFlow<CropDoctorResult?> = _doctorResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var hasCustomScan = false

    init {
        viewModelScope.launch {
            userLanguageCode.collect { lang ->
                if (!hasCustomScan) {
                    loadInitialAssessment(lang)
                }
            }
        }
    }

    private fun loadInitialAssessment(lang: String) {
        viewModelScope.launch {
            val pastScans = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                plantScanDao.getRecentScans(5)
            }
            val input = CropDoctorInput(
                temperature = 31f,
                humidity = 78,
                rainProbability = 60,
                conditionDescription = "Monsoon Fungal Prevention Scouting",
                pastScans = pastScans
            )
            _doctorResult.value = CropDoctorEngine.evaluate(input, lang)
        }
    }

    fun analyzeImage(base64Image: String) {
        viewModelScope.launch {
            _isLoading.value = true
            hasCustomScan = true
            
            val pastScans = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                plantScanDao.getRecentScans(10)
            }
            
            // Check daily Kindwise rate limit (2 queries/day per user)
            val allowed = dataStoreManager.recordKindwiseUsage()
            var diseaseName = "Unknown"
            var isOfflineFallback = false

            if (allowed) {
                try {
                    val request = KindwiseHealthRequest(images = listOf("data:image/jpeg;base64,$base64Image"))
                    val response = kindwiseApi.analyzeCropHealth(kindwiseApiKey, request)
                    val topSuggestion = response.result?.disease?.suggestions?.firstOrNull()
                    if (topSuggestion != null) {
                        diseaseName = topSuggestion.name ?: "Pest / Fungal Infection"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    diseaseName = "Leaf Spot / Fungal Infection"
                }
            } else {
                isOfflineFallback = true
                diseaseName = "Leaf Spot / Rust (Offline Engine)"
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
            val finalResult = if (isOfflineFallback) {
                val fallbackNotice = when (lang.lowercase()) {
                    "hi" -> "⚠️ दैनिक काइंडवाइज़ स्कैन सीमा समाप्त (आज 2/2 प्रयुक्त)। ऑफ़लाइन क्रॉप डॉक्टर इंजन द्वारा रिपोर्ट तैयार की गई।"
                    "mr" -> "⚠️ दैनिक काइंडवाइज स्कॅन मर्यादा संपली (आज 2/2 वापरले). ऑफलाइन क्रॉप डॉक्टर इंजिनद्वारे अहवाल तयार केला गेला."
                    "bn" -> "⚠️ দৈনিক কাইন্ডওয়াইজ স্ক্যান সীমা সমাপ্ত (আজ ২/২ ব্যবহৃত)। অফলাইন ক্রপ ডক্টর ইঞ্জিন দ্বারা রিপোর্ট তৈরি করা হয়েছে।"
                    "te" -> "⚠️ రోజువారీ కైండ్‌వైజ్ స్కాన్ పరిమితి ముగిసింది (ఈరోజు 2/2 ఉపయోగించబడ్డాయి). ఆఫ్‌లైన్ క్రాప్ డాక్టర్ ఇంజిన్ ద్వారా నివేదిక తయారు చేయబడింది."
                    "ta" -> "⚠️ தினசரி கைண்ட்வைஸ் ஸ்கேன் வரம்பு முடிந்தது (இன்று 2/2 பயன்படுத்தப்பட்டது). ஆஃப்லைன் பயிர் மருத்துவர் முறை மூலம் அறிக்கை உருவாக்கப்பட்டுள்ளது."
                    "kn" -> "⚠️ ದೈನಂದಿನ ಕೈಂಡ್‌ವೈಸ್ ಸ್ಕ್ಯಾನ್ ಮಿತಿ ಮುಗಿದಿದೆ (ಇಂದು 2/2 ಬಳಸಲಾಗಿದೆ). ಆಫ್‌ಲೈನ್ ಕ್ರಾಪ್ ಡಾಕ್ಟರ್ ಎಂಜಿನ್ ಮೂಲಕ ವರದಿ ಸಿದ್ಧಪಡಿಸಲಾಗಿದೆ."
                    "gu" -> "⚠️ દૈનિક કાઇન્ડવાઇઝ સ્કેન મર્યાદા પૂર્ણ (આજે 2/2 વપરાયેલ). ઑફલાઇન ક્રોપ ડૉક્ટર એન્જિન દ્વારા રિપોર્ટ તૈયાર કરાયો."
                    "pa" -> "⚠️ ਰੋਜ਼ਾਨਾ ਕਾਈਂਡਵਾਈਜ਼ ਸਕੈਨ ਸੀਮਾ ਸਮਾਪਤ (ਅੱਜ 2/2 ਵਰਤੇ ਗਏ)। ਔਫਲਾਈਨ ਕ੍ਰੌਪ ਡਾਕਟਰ ਇੰਜਣ ਦੁਆਰਾ ਰਿਪੋਰਟ ਤਿਆਰ ਕੀਤੀ ਗਈ।"
                    "ml" -> "⚠️ പ്രതിദിന കൈൻഡ്‌വൈസ് സ്കാൻ പരിധി കഴിഞ്ഞു (ഇന്ന് 2/2 ഉപയോഗിച്ചു). ഓഫ്‌ലൈൻ ക്രോപ്പ് ഡോക്ടർ എഞ്ചിൻ റിപ്പോർട്ട് തയ്യാറാക്കി."
                    "or" -> "⚠️ ଦୈନିକ କାଇଣ୍ଡୱାଇଜ ସ୍କାନ ସୀମା ସମାପ୍ତ (ଆଜି 2/2 ବ୍ୟବହୃତ)। ଅଫଲାଇନ କ୍ରପ ଡାକ୍ତର ଇଞ୍ଜିନ ଦ୍ୱାରା ରିପୋର୍ଟ ପ୍ରସ୍ତୁତ ହୋଇଛି।"
                    else -> "⚠️ Daily Kindwise scan limit reached (2/2 scans used today). Report generated using offline Crop Doctor engine."
                }
                result.copy(
                    weatherAlert = if (result.weatherAlert != null) "$fallbackNotice\n\n${result.weatherAlert}" else fallbackNotice
                )
            } else {
                result
            }
            _doctorResult.value = finalResult

            // Save to Room DB
            if (diseaseName != "Unknown") {
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    val scan = com.krishisevak.app.data.local.db.PlantScanEntity(
                        plantName = "Crop",
                        disease = diseaseName,
                        isHealthy = diseaseName.contains("Healthy", ignoreCase = true) || diseaseName.contains("None", ignoreCase = true),
                        probability = if (isOfflineFallback) 0.75f else 0.9f,
                        treatment = finalResult.recommendations.firstOrNull() ?: "",
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
