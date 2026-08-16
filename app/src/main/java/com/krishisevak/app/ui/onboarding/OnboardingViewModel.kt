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
            
            val langCode = _selectedLanguage.value.code
            val speechLines = when (langCode) {
                "hi" -> listOf(
                    "कृषि सेवक में आपका स्वागत है।",
                    "आप कैसे आगे बढ़ना चाहते हैं?",
                    "आगे बढ़ने के लिए ओटीपी से लॉगिन करें, या बिना लॉगिन के जारी रखें।"
                )
                "bn" -> listOf(
                    "কৃষি সেবকে আপনাকে স্বাগতম।",
                    "আপনি কীভাবে এগিয়ে যেতে চান?",
                    "এগিয়ে যাওয়ার জন্য ওটিপি দিয়ে লগইন করুন বা লগইন ছাড়াই চালিয়ে যান।"
                )
                "mr" -> listOf(
                    "कृषि सेवक मध्ये आपले स्वागत आहे.",
                    "तुम्हाला पुढे कसे जायचे आहे?",
                    "पुढे जाण्यासाठी ओटीपी द्वारे लॉगिन करा किंवा लॉगिन शिवाय सुरू ठेवा."
                )
                "te" -> listOf(
                    "కృషి సేవక్‌కి స్వాగతం.",
                    "మీరు ఎలా ముందుకు సాగాలనుకుంటున్నారు?",
                    "ముందుకు సాగడానికి ఓటీపీతో లాగిన్ అవ్వండి లేదా లాగిన్ లేకుండా కొనసాగించండి."
                )
                "ta" -> listOf(
                    "கிருஷி சேவக் உங்களை வரவேற்கிறது.",
                    "நீங்கள் எவ்வாறு தொடர விரும்புகிறீர்கள்?",
                    "தொடர OTP மூலம் உள்நுழையவும் அல்லது உள்நுழையாமல் தொடரவும்."
                )
                "kn" -> listOf(
                    "ಕೃಷಿ ಸೇವಕ್‌ಗೆ ಸುಸ್ವಾಗತ.",
                    "ನೀವು ಹೇಗೆ ಮುಂದುವರಿಯಲು ಬಯಸುತ್ತೀರಿ?",
                    "ಮುಂದುವರಿಯಲು ಒಟಿಪಿ ಮೂಲಕ ಲಾಗಿನ್ ಆಗಿ ಅಥವಾ ಲಾಗಿನ್ ಇಲ್ಲದೆ ಮುಂದುವರಿಯಿರಿ."
                )
                "ml" -> listOf(
                    "കൃഷി സേവകിലേക്ക് സ്വാഗതം.",
                    "നിങ്ങൾ എങ്ങനെ തുടരാൻ ആഗ്രഹിക്കുന്നു?",
                    "തുടരുന്നതിന് ഒടിപി ഉപയോഗിച്ച് ലോഗിൻ ചെയ്യുക അല്ലെങ്കിൽ ലോഗിൻ ചെയ്യാതെ തുടരുക."
                )
                "gu" -> listOf(
                    "કૃષિ સેવકમાં આપનું સ્વાગત છે.",
                    "તમે કેવી રીતે આગળ વધવા માંગો છો?",
                    "આગળ વધવા માટે ઓટીપી વડે લોગિન કરો અથવા લોગિન વિના ચાલુ રાખો."
                )
                "pa" -> listOf(
                    "ਕ੍ਰਿਸ਼ੀ ਸੇਵਕ ਵਿੱਚ ਤੁਹਾਡਾ ਸੁਆਗਤ ਹੈ।",
                    "ਤੁਸੀਂ ਕਿਵੇਂ ਅੱਗੇ ਵਧਣਾ ਚਾਹੁੰਦੇ ਹੋ?",
                    "ਅੱਗੇ ਵਧਣ ਲਈ ਓਟੀਪੀ ਨਾਲ ਲੌਗਇਨ ਕਰੋ ਜਾਂ ਬਿਨਾਂ ਲੌਗਇਨ ਕੀਤੇ ਜਾਰੀ ਰੱਖੋ।"
                )
                "or" -> listOf(
                    "କୃଷି ସେବକକୁ ଆପଣଙ୍କୁ ସ୍ୱାଗତ।",
                    "ଆପଣ କିପରି ଆଗକୁ ବଢ଼ିବାକୁ ଚାହାଁନ୍ତି?",
                    "ଆଗକୁ ବଢ଼ିବା ପାଇଁ ଓଟିପି ଦ୍ୱାରା ଲଗଇନ୍ କରନ୍ତୁ କିମ୍ବା ଲଗଇନ୍ ବିନା ଜାରି ରଖନ୍ତୁ।"
                )
                else -> listOf(
                    "Welcome to Krishi Sevak.",
                    "How would you like to proceed?",
                    "Login with OTP or continue without login."
                )
            }

            for ((index, lineText) in speechLines.withIndex()) {
                if (!isSpeechRunning) break
                ttsManager.speakAndWait(
                    "onboarding_${index}_$langCode",
                    lineText,
                    langCode
                )
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
