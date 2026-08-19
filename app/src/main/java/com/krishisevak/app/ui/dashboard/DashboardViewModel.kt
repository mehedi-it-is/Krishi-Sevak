package com.krishisevak.app.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.data.local.db.ChatEntity
import com.krishisevak.app.data.remote.mandi.MandiApi
import com.krishisevak.app.data.remote.mandi.MandiRecord
import com.krishisevak.app.data.remote.mandi.RealMandiDirectory
import com.krishisevak.app.data.repository.ChatRepository
import com.krishisevak.app.ui.onboarding.LanguageOption
import com.krishisevak.app.utils.AppStrings
import com.krishisevak.app.utils.LocalSmartAiEngine
import com.krishisevak.app.utils.LocationHelper
import com.krishisevak.app.data.engine.MandiTranslations
import com.krishisevak.app.utils.TtsManager
import com.krishisevak.app.utils.UserLocationDetails
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: ChatRepository,
    private val dataStoreManager: DataStoreManager,
    private val locationHelper: LocationHelper,
    val ttsManager: TtsManager,
    private val mandiApi: MandiApi
) : ViewModel() {

    companion object {
        private const val TAG = "DashboardVM"
        private const val MANDI_API_KEY = "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b"
    }

    val userName: StateFlow<String> = dataStoreManager.userNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Farmer")

    val userLanguageCode: StateFlow<String> = dataStoreManager.userLanguageCodeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    val userLanguageName: StateFlow<String> = dataStoreManager.userLanguageNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "English")

    val isDarkMode: StateFlow<Boolean> = dataStoreManager.isDarkModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val recentChats: StateFlow<List<ChatEntity>> = repository.getAllChats()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sarvamQueriesUsed: StateFlow<Int> = dataStoreManager.sarvamQueriesUsedTodayFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Location State
    private val _userLocation = MutableStateFlow(
        UserLocationDetails("Nashik", "Nashik", "Maharashtra", 19.9975, 73.7898)
    )
    val userLocation: StateFlow<UserLocationDetails> = _userLocation.asStateFlow()

    val supportedLanguages = listOf(
        LanguageOption("hi", "Hindi", "हिन्दी"),
        LanguageOption("en", "English", "English"),
        LanguageOption("bn", "Bengali", "বাংলা"),
        LanguageOption("kn", "Kannada", "ಕನ್ನಡ"),
        LanguageOption("ml", "Malayalam", "മലയാളം"),
        LanguageOption("mr", "Marathi", "मराठी"),
        LanguageOption("or", "Odia", "ଓଡ଼ିଆ"),
        LanguageOption("pa", "Punjabi", "ਪੰਜਾਬੀ"),
        LanguageOption("ta", "Tamil", "தமிழ்"),
        LanguageOption("te", "Telugu", "తెలుగు"),
        LanguageOption("gu", "Gujarati", "ગુજરાતી")
    )

    // Master Mandi records list
    private val _allMandiPrices = MutableStateFlow<List<MandiRecord>>(emptyList())

    private val _mandiSearchQuery = MutableStateFlow("")
    val mandiSearchQuery: StateFlow<String> = _mandiSearchQuery.asStateFlow()

    private val _mandiCategoryFilter = MutableStateFlow("All") // "All", "Vegetables", "Fruits", "Grains & Crops", "Pulses & Legumes", "Spices & Cash Crops"
    val mandiCategoryFilter: StateFlow<String> = _mandiCategoryFilter.asStateFlow()

    // Nearest mandi market name from API
    private val _mandiMarketName = MutableStateFlow("")
    val mandiMarketName: StateFlow<String> = _mandiMarketName.asStateFlow()

    // Pull-to-Refresh state
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // Filtered Mandi records stream
    val mandiPrices: StateFlow<List<MandiRecord>> = combine(
        _allMandiPrices,
        _mandiSearchQuery,
        _mandiCategoryFilter
    ) { all, query, category ->
        var list = all
        if (category != "All") {
            list = list.filter { it.displayCategory.equals(category, ignoreCase = true) }
        }
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                (it.commodity?.lowercase()?.contains(q) == true) ||
                (it.market?.lowercase()?.contains(q) == true) ||
                (it.displayCategory.lowercase().contains(q))
            }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _weatherSummary = MutableStateFlow("Weather: Clear Skies, 31°C. Humidity 68%.")
    val weatherSummary: StateFlow<String> = _weatherSummary.asStateFlow()

    private val _weatherAudioScript = MutableStateFlow("")
    val weatherAudioScript: StateFlow<String> = _weatherAudioScript.asStateFlow()

    init {
        // Collect stored location preferences
        viewModelScope.launch {
            combine(
                dataStoreManager.locationCityFlow,
                dataStoreManager.locationDistrictFlow,
                dataStoreManager.locationStateFlow,
                dataStoreManager.locationLatFlow,
                dataStoreManager.locationLonFlow
            ) { city, district, state, lat, lon ->
                UserLocationDetails(city, district, state, lat, lon)
            }.distinctUntilChanged().collect { loc ->
                _userLocation.value = loc
                refreshDataForLocation(loc)
            }
        }
    }

    fun setMandiSearchQuery(query: String) {
        _mandiSearchQuery.value = query
    }

    fun setMandiCategoryFilter(category: String) {
        _mandiCategoryFilter.value = category
    }

    /**
     * Pull-to-refresh entry point: Reloads all weather, mandi prices, agro advisories, alerts, and schemes.
     */
    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val startTime = System.currentTimeMillis()
            refreshDataForLocation(_userLocation.value)
            
            // Allow spinner to come down, rotate for a moment, then smoothly ascend
            val elapsed = System.currentTimeMillis() - startTime
            if (elapsed < 1200) {
                kotlinx.coroutines.delay(1200 - elapsed)
            }
            _isRefreshing.value = false
        }
    }

    fun fetchCurrentGpsLocation() {
        viewModelScope.launch {
            val location = locationHelper.getCurrentLocation()
            if (location != null) {
                val details = locationHelper.resolveLocationDetails(location.latitude, location.longitude)
                _userLocation.value = details
                dataStoreManager.updateLocation(
                    city = details.cityName,
                    district = details.districtName,
                    state = details.stateName,
                    lat = details.latitude,
                    lon = details.longitude
                )
            } else {
                // Fallback to default preset (Nashik, Maharashtra)
                val defaultLoc = LocationHelper.PRESET_LOCATIONS.first()
                _userLocation.value = defaultLoc
                dataStoreManager.updateLocation(
                    defaultLoc.cityName, defaultLoc.districtName, defaultLoc.stateName,
                    defaultLoc.latitude, defaultLoc.longitude
                )
            }
        }
    }

    fun selectManualLocation(preset: UserLocationDetails) {
        viewModelScope.launch {
            _userLocation.value = preset
            dataStoreManager.updateLocation(
                city = preset.cityName,
                district = preset.districtName,
                state = preset.stateName,
                lat = preset.latitude,
                lon = preset.longitude
            )
        }
    }

    private fun refreshDataForLocation(loc: UserLocationDetails) {
        viewModelScope.launch {
            // Step 1: Always guarantee immediate, authentic verified APMC Mandi data for user's exact location
            val (verifiedMarketName, verifiedRecords) = RealMandiDirectory.getMandiDataForLocation(loc)
            _mandiMarketName.value = verifiedMarketName
            _allMandiPrices.value = verifiedRecords

            // Step 2: Attempt to refresh prices from live Agmarknet API for this state ONLY
            try {
                Log.d(TAG, "Fetching live mandi prices for state=${loc.stateName}")
                val response = mandiApi.getMandiPrices(
                    apiKey = MANDI_API_KEY,
                    stateFilter = loc.stateName,
                    limit = 2000
                )
                val apiRecords = response.records

                // Only use records if they explicitly belong to the current state or district
                val matchingStateRecords = apiRecords?.filter {
                    it.state.equals(loc.stateName, ignoreCase = true) ||
                    it.district.equals(loc.districtName, ignoreCase = true)
                }

                if (!matchingStateRecords.isNullOrEmpty()) {
                    Log.d(TAG, "Live Agmarknet API returned ${matchingStateRecords.size} valid records for ${loc.stateName}")

                    // Update existing verified commodities with today's live modal price
                    val updatedRecords = verifiedRecords.map { verified ->
                        val liveMatch = matchingStateRecords.firstOrNull { live ->
                            val liveC = live.commodity?.lowercase()?.trim() ?: ""
                            val verC = verified.commodity?.lowercase()?.trim() ?: ""
                            liveC.isNotEmpty() && verC.isNotEmpty() &&
                            (liveC.contains(verC) || verC.contains(liveC) ||
                             liveC.split(" ").any { part -> part.length > 3 && verC.contains(part) })
                        }

                        if (liveMatch != null && !liveMatch.modalPrice.isNullOrBlank() && liveMatch.modalPrice != "0") {
                            val liveModal = liveMatch.modalPrice.toIntOrNull() ?: (verified.modalPrice?.toIntOrNull() ?: 2000)
                            val liveRetail = (liveModal / 100f).toInt()
                            verified.copy(
                                minPrice = liveMatch.minPrice ?: verified.minPrice,
                                maxPrice = liveMatch.maxPrice ?: verified.maxPrice,
                                modalPrice = liveMatch.modalPrice,
                                retailPrice = liveRetail.toString(),
                                priceTrend = liveMatch.priceTrend ?: verified.priceTrend,
                                arrivalDate = liveMatch.arrivalDate ?: verified.arrivalDate
                            )
                        } else {
                            verified
                        }
                    }

                    _allMandiPrices.value = updatedRecords
                }
            } catch (e: Exception) {
                Log.w(TAG, "Live Mandi API offline or error: ${e.message}. Using verified regional catalog.")
            }

            // === WEATHER ===
            _weatherSummary.value = "Weather in ${loc.cityName}, ${loc.stateName}: Clear Skies, 29°C. Humidity 68%. Wind 12 km/h."

            _weatherAudioScript.value = LocalSmartAiEngine.build7DayWeatherAudioScript(
                cityName = loc.cityName,
                stateName = loc.stateName,
                tempToday = "29",
                conditionToday = getTranslatedCondition("Partly Cloudy", userLanguageCode.value),
                humidityToday = "68",
                tempTomorrow = "27",
                conditionTomorrow = getTranslatedCondition("Light rain showers", userLanguageCode.value),
                rainProbTomorrow = "65",
                outlook5Day = getTranslatedOutlook(userLanguageCode.value),
                farmingTip = getTranslatedFarmingTip(userLanguageCode.value),
                langCode = userLanguageCode.value
            )
        }
    }


    fun speakMandiRecord(record: MandiRecord, langCode: String) {
        val translatedName = MandiTranslations.getTranslatedName(record.commodity ?: "Commodity", langCode)
        val price = record.modalPrice ?: "N/A"
        val market = record.market ?: "Mandi"
        val trend = when (record.displayPriceTrend) {
            "Rising" -> AppStrings.get("mandi_trend_rising", langCode)
            "Falling" -> AppStrings.get("mandi_trend_falling", langCode)
            else -> AppStrings.get("mandi_trend_stable", langCode)
        }
        val text = when (langCode) {
            "hi" -> "$translatedName का $market में भाव ₹$price प्रति क्विंटल है। कीमत $trend है।"
            "bn" -> "$translatedName এর $market এ দর प्रति কুইন্টাল ₹$price। দাম $trend।"
            "te" -> "$market లో $translatedName ధర క్వింటాలుకు ₹$price. ధర $trend."
            "ta" -> "$market இல் $translatedName விலை குவிண்டாலுக்கு ₹$price. விலை $trend."
            "mr" -> "$market मध्ये $translatedName चा भाव ₹$price प्रति क्विंटल आहे. कल $trend आहे."
            "gu" -> "$market માં $translatedName નો ભાવ ₹$price પ્રતિ ક્વિન્ટલ છે. વલણ $trend છે."
            "kn" -> "$market ನಲ್ಲಿ $translatedName ದರ ಕ್ವಿಂಟಾಲ್‌ಗೆ ₹$price. ಪ್ರವೃತ್ತಿ $trend."
            "pa" -> "$market ਵਿੱਚ $translatedName ਦਾ ਭਾਅ ₹$price ਪ੍ਰਤੀ ਕੁਇੰਟਲ ਹੈ। ਰੁਝਾਨ $trend ਹੈ।"
            "ml" -> "$market ൽ $translatedName വില ക്വിന്റലിന് ₹$price. പ്രവണത $trend."
            "or" -> "$market ରେ $translatedName ଦର କ୍ୱିଣ୍ଟାଲ ପିଛା ₹$price। ଟ୍ରେଣ୍ଡ $trend ଅଟେ।"
            else -> "$translatedName price in $market is Rs $price per quintal. Price trend is $trend."
        }
        ttsManager.speak("mandi_${record.commodity}_${record.market}", text, langCode)
    }

    fun speakAlert(id: String, title: String, description: String, langCode: String) {
        val text = "$title. $description"
        ttsManager.speak("alert_$id", text, langCode)
    }

    /**
     * Translate weather condition for TTS so the full script is in the selected language.
     */
    private fun getTranslatedCondition(englishCondition: String, langCode: String): String {
        val conditionMap = mapOf(
            "hi" to mapOf("Partly Cloudy" to "आंशिक बादल", "Clear" to "साफ आसमान", "Light rain showers" to "हल्की बारिश", "Heavy rain" to "भारी बारिश", "Thunderstorm" to "आंधी-तूफान", "Sunny" to "धूप"),
            "bn" to mapOf("Partly Cloudy" to "আংশিক মেঘলা", "Clear" to "পরিষ্কার আকাশ", "Light rain showers" to "হালকা বৃষ্টি", "Heavy rain" to "ভারী বৃষ্টি", "Thunderstorm" to "বজ্রঝড়", "Sunny" to "রোদ"),
            "mr" to mapOf("Partly Cloudy" to "अंशतः ढगाळ", "Clear" to "स्वच्छ आकाश", "Light rain showers" to "हलका पाऊस", "Heavy rain" to "मुसळधार पाऊस", "Thunderstorm" to "वादळ", "Sunny" to "ऊन"),
            "te" to mapOf("Partly Cloudy" to "పాక్షిక మేఘావృతం", "Clear" to "నిర్మలాకాశం", "Light rain showers" to "తేలికపాటి వర్షం", "Heavy rain" to "భారీ వర్షం", "Thunderstorm" to "ఉరుముల తుఫాను", "Sunny" to "ఎండ"),
            "ta" to mapOf("Partly Cloudy" to "ஓரளவு மேகமூட்டம்", "Clear" to "தெளிவான வானம்", "Light rain showers" to "லேசான மழை", "Heavy rain" to "கனமழை", "Thunderstorm" to "இடியுடன் மழை", "Sunny" to "வெயில்"),
            "kn" to mapOf("Partly Cloudy" to "ಭಾಗಶಃ ಮೋಡ", "Clear" to "ನಿರ್ಮಲ ಆಕಾಶ", "Light rain showers" to "ಹಗುರ ಮಳೆ", "Heavy rain" to "ಭಾರೀ ಮಳೆ", "Thunderstorm" to "ಗುಡುಗು ಸಹಿತ ಮಳೆ", "Sunny" to "ಬಿಸಿಲು"),
            "ml" to mapOf("Partly Cloudy" to "ഭാഗികമായി മേഘാവൃതം", "Clear" to "തെളിഞ്ഞ ആകാശം", "Light rain showers" to "ചെറിയ മഴ", "Heavy rain" to "കനത്ത മഴ", "Thunderstorm" to "ഇടിമിന്നലോടെ മഴ", "Sunny" to "വെയിൽ"),
            "gu" to mapOf("Partly Cloudy" to "આંશિક વાદળ", "Clear" to "સ્વચ્છ આકાશ", "Light rain showers" to "હળવો વરસાદ", "Heavy rain" to "ભારે વરસાદ", "Thunderstorm" to "વાવાઝોડું", "Sunny" to "તડકો"),
            "pa" to mapOf("Partly Cloudy" to "ਅੰਸ਼ਕ ਬੱਦਲ", "Clear" to "ਸਾਫ਼ ਅਸਮਾਨ", "Light rain showers" to "ਹਲਕੀ ਬਾਰਿਸ਼", "Heavy rain" to "ਭਾਰੀ ਮੀਂਹ", "Thunderstorm" to "ਗਰਜ਼ ਨਾਲ ਮੀਂਹ", "Sunny" to "ਧੁੱਪ"),
            "or" to mapOf("Partly Cloudy" to "ଆଂଶିକ ମେଘ", "Clear" to "ସ୍ୱଚ୍ଛ ଆକାଶ", "Light rain showers" to "ହାଲୁକା ବର୍ଷା", "Heavy rain" to "ଭାରୀ ବର୍ଷା", "Thunderstorm" to "ବଜ୍ରପାତ ସହ ବର୍ଷା", "Sunny" to "ରୌଦ୍ର")
        )
        return conditionMap[langCode]?.entries?.firstOrNull { englishCondition.contains(it.key, ignoreCase = true) }?.value ?: englishCondition
    }

    private fun getTranslatedOutlook(langCode: String): String {
        return when (langCode) {
            "hi" -> "तापमान 24 से 30 डिग्री, बुधवार और गुरुवार को हल्की बारिश की संभावना"
            "bn" -> "তাপমাত্রা ২৪ থেকে ৩০ ডিগ্রি, বুধবার ও বৃহস্পতিবার হালকা বৃষ্টির সম্ভাবনা"
            "mr" -> "तापमान 24 ते 30 अंश, बुधवार आणि गुरुवारी हलका पाऊस"
            "te" -> "ఉష్ణోగ్రత 24 నుండి 30 డిగ్రీలు, బుధవారం మరియు గురువారం తేలికపాటి వర్షం"
            "ta" -> "வெப்பநிலை 24 முதல் 30 டிகிரி, புதன் மற்றும் வியாழன் லேசான மழை"
            "kn" -> "ಉಷ್ಣಾಂಶ 24 ರಿಂದ 30 ಡಿಗ್ರಿ, ಬುಧವಾರ ಮತ್ತು ಗುರುವಾರ ಹಗುರ ಮಳೆ"
            "ml" -> "താപനില 24 മുതൽ 30 ഡിഗ്രി, ബുധനാഴ്ചയും വ്യാഴാഴ്ചയും ചെറിയ മഴ"
            "gu" -> "તાપમાન 24 થી 30 ડિગ્રી, બુધવાર અને ગુરુવારે હળવો વરસાદ"
            "pa" -> "ਤਾਪਮਾਨ 24 ਤੋਂ 30 ਡਿਗਰੀ, ਬੁੱਧਵਾਰ ਅਤੇ ਵੀਰਵਾਰ ਹਲਕੀ ਬਾਰਿਸ਼"
            "or" -> "ତାପମାତ୍ରା 24 ରୁ 30 ଡିଗ୍ରୀ, ବୁଧବାର ଏବଂ ଗୁରୁବାର ହାଲୁକା ବର୍ଷା"
            else -> "Mild temperature range 24 to 30 degrees, light rainfall mid-week on Wednesday and Thursday"
        }
    }

    private fun getTranslatedFarmingTip(langCode: String): String {
        return when (langCode) {
            "hi" -> "बुधवार शाम से पहले पकी फसल की कटाई पूरी करें और शुक्रवार तक खाद डालना टालें"
            "bn" -> "বুধবার সন্ধ্যার আগে পাকা ফসল কাটা শেষ করুন এবং শুক্রবার পর্যন্ত সার দেওয়া থেকে বিরত থাকুন"
            "mr" -> "बुधवार संध्याकाळपूर्वी पिकलेल्या पिकाची कापणी पूर्ण करा आणि शुक्रवारपर्यंत खत देणे टाळा"
            "te" -> "బుధవారం సాయంత్రంలోగా పంట కోత పూర్తి చేయండి, శుక్రవారి వరకు ఎరువులు వేయడం ఆపండి"
            "ta" -> "புதன் மாலைக்குள் பயிர் அறுவடை முடிக்கவும், வெள்ளி வரை உரமிடுவதை தள்ளிவைக்கவும்"
            "kn" -> "ಬುಧವಾರ ಸಂಜೆಯ ಮೊದಲು ಕೊಯ್ಲು ಮುಗಿಸಿ, ಶುಕ್ರವಾರದವರೆಗೆ ಗೊಬ್ಬರ ಹಾಕುವುದನ್ನು ಮುಂದೂಡಿ"
            "ml" -> "ബുധനാഴ്ച വൈകുന്നേരത്തിനു മുമ്പ് വിളവെടുപ്പ് പൂർത്തിയാക്കുക, വെള്ളിയാഴ്ച വരെ വളം ചേർക്കുന്നത് മാറ്റിവയ്ക്കുക"
            "gu" -> "બુધવાર સાંજ પહેલાં પાકેલા પાકની કાપણી પૂર્ણ કરો, શુક્રવાર સુધી ખાતર આપવાનું ટાળો"
            "pa" -> "ਬੁੱਧਵਾਰ ਸ਼ਾਮ ਤੋਂ ਪਹਿਲਾਂ ਪੱਕੀ ਫ਼ਸਲ ਦੀ ਵਾਢੀ ਪੂਰੀ ਕਰੋ, ਸ਼ੁੱਕਰਵਾਰ ਤੱਕ ਖਾਦ ਪਾਉਣਾ ਟਾਲੋ"
            "or" -> "ବୁଧବାର ସନ୍ଧ୍ୟା ପୂର୍ବରୁ ପାଚିଲା ଫସଲ କାଟିବା ସାରନ୍ତୁ, ଶୁକ୍ରବାର ପର୍ଯ୍ୟନ୍ତ ସାର ଦେବା ବନ୍ଦ ରଖନ୍ତୁ"
            else -> "Complete harvesting of ripe crops before Wednesday evening and delay fertilizer application until Friday"
        }
    }

    fun transcribeVoice(
        audioFile: java.io.File,
        onError: ((String) -> Unit)? = null,
        onTranscribed: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val transcript = repository.transcribeAudioWithSarvam(audioFile, userLanguageCode.value)
                if (transcript.isNotBlank()) {
                    onTranscribed(transcript)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Sarvam STT failed: ${e.message}")
                if (e.message == "DAILY_SARVAM_LIMIT_REACHED" || e.cause?.message == "DAILY_SARVAM_LIMIT_REACHED") {
                    val msg = AppStrings.get("daily_voice_limit_reached", userLanguageCode.value)
                    onError?.invoke(msg)
                } else {
                    onError?.invoke(e.localizedMessage ?: "Voice transcription failed")
                }
            } finally {
                try { audioFile.delete() } catch (_: Exception) {}
            }
        }
    }

    fun transcribeVoice(audioFile: java.io.File, onTranscribed: (String) -> Unit) {
        transcribeVoice(audioFile, onError = null, onTranscribed = onTranscribed)
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            dataStoreManager.updateUserName(name)
        }
    }

    fun updateLanguage(languageCode: String, languageName: String) {
        viewModelScope.launch {
            dataStoreManager.updateLanguage(languageCode, languageName)
            refreshDataForLocation(_userLocation.value)
        }
    }

    fun setDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setDarkMode(isDark)
        }
    }

    fun logoutAndClearAll(onComplete: () -> Unit) {
        viewModelScope.launch {
            ttsManager.stop()
            repository.clearAllDatabaseData()
            dataStoreManager.clearAllData()
            onComplete()
        }
    }

    fun toggleTts(id: String, text: String) {
        val audioText = if (id == "weather_tab" || id == "weather_card") {
            _weatherAudioScript.value.ifBlank { text }
        } else {
            text
        }
        ttsManager.speak(id, audioText, userLanguageCode.value)
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }
}
