package com.kisaandost.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisaandost.app.data.local.datastore.DataStoreManager
import com.kisaandost.app.utils.TtsManager
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
        stopAutoSpeech()
        startAutoSpeech()
    }

    fun onNameChange(name: String) {
        _userName.value = name
    }

    fun startAutoSpeech() {
        if (isSpeechRunning) return
        isSpeechRunning = true
        speechJob = viewModelScope.launch {
            delay(1200) // Initial delay for TTS engine initialization
            
            // Per requirement: UI is in English, but TTS specifically speaks in Hindi on first login screen
            val hindiLines = listOf(
                "किसान दोस्त में आपका स्वागत है।",
                "आप कैसे आगे बढ़ना चाहते हैं?",
                "आगे बढ़ने के लिए ओटीपी से लॉगिन करें, या बिना लॉगिन के जारी रखें।"
            )

            for ((index, lineText) in hindiLines.withIndex()) {
                if (!isSpeechRunning) break
                // Use speakAndWait with "hi" language code
                ttsManager.speakAndWait(
                    "onboarding_${index}_hi",
                    lineText,
                    "hi"
                )
                // Adequate pause between lines for natural cadence
                if (isSpeechRunning) {
                    delay(1200)
                }
            }
        }
    }

    fun stopAutoSpeech() {
        isSpeechRunning = false
        speechJob?.cancel()
        speechJob = null
        ttsManager.stop()
    }

    fun saveOnboarding(nameInput: String?, onSuccess: () -> Unit) {
        stopAutoSpeech()
        val finalName = (nameInput ?: _userName.value).ifBlank { "Mehedi" }
        viewModelScope.launch {
            dataStoreManager.saveUserOnboarding(
                name = finalName.trim(),
                languageCode = _selectedLanguage.value.code,
                languageName = _selectedLanguage.value.name
            )
            onSuccess()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAutoSpeech()
    }
}
