package com.krishisevak.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.utils.TtsManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LanguageOption(val code: String, val name: String, val nativeName: String)

class OnboardingViewModel(
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager
) : ViewModel() {

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

    private val _selectedLanguage = MutableStateFlow(supportedLanguages.first())
    val selectedLanguage: StateFlow<LanguageOption> = _selectedLanguage.asStateFlow()

    private val _userName = MutableStateFlow("Mehedi")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private var speechJob: Job? = null
    private var isSpeechRunning = false

    fun selectLanguage(option: LanguageOption) {
        _selectedLanguage.value = option
    }

    fun onNameChange(name: String) {
        _userName.value = name
    }

    fun startAutoSpeech() {
        if (isSpeechRunning) return
        isSpeechRunning = true
        speechJob = viewModelScope.launch {
            delay(500) // Short delay for UI render
            if (!isSpeechRunning) return@launch
            
            // Speak continuously in Hindi on the login / language selection screen without pauses
            val fullGreeting = "कृषि सेवक में आपका स्वागत है। आप कैसे आगे बढ़ना चाहते हैं? आगे बढ़ने के लिए ओटीपी से लॉगिन करें, या बिना लॉगिन के जारी रखें।"
            ttsManager.speak(
                "onboarding_hi_welcome",
                fullGreeting,
                "hi"
            )
        }
    }

    fun stopAutoSpeech() {
        isSpeechRunning = false
        speechJob?.cancel()
        speechJob = null
        ttsManager.stop()
    }

    fun saveOnboarding(nameInput: String?, onSuccess: (selectedLangCode: String) -> Unit) {
        stopAutoSpeech()
        val finalName = (nameInput ?: _userName.value).ifBlank { "Mehedi" }
        val langCode = _selectedLanguage.value.code
        val langName = _selectedLanguage.value.name
        viewModelScope.launch {
            dataStoreManager.saveUserOnboarding(
                name = finalName.trim(),
                languageCode = langCode,
                languageName = langName
            )
            onSuccess(langCode)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAutoSpeech()
    }
}
