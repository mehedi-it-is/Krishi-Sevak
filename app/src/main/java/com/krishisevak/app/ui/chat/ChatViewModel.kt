package com.krishisevak.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.data.local.db.MessageEntity
import com.krishisevak.app.data.repository.ChatRepository
import com.krishisevak.app.utils.LanguageDetector
import com.krishisevak.app.utils.TtsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

sealed interface ChatUiState {
    data object Idle : ChatUiState
    data object Loading : ChatUiState
    data class Success(val chatId: String) : ChatUiState
    data class Error(val message: String) : ChatUiState
}

class ChatViewModel(
    private val repository: ChatRepository,
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager,
    private val existingChatId: String? = null
) : ViewModel() {

    val currentChatId: String = existingChatId ?: UUID.randomUUID().toString()
    private var isNewChatCreated = false

    val messages: StateFlow<List<MessageEntity>> = repository.getMessagesForChat(currentChatId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sarvamQueriesUsed: StateFlow<Int> = dataStoreManager.sarvamQueriesUsedTodayFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val kindwiseQueriesUsed: StateFlow<Int> = dataStoreManager.kindwiseQueriesUsedTodayFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Idle)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _userLanguageCode = MutableStateFlow("hi")
    private val _userLanguageName = MutableStateFlow("Hindi")

    private val _lastDetectedLanguage = MutableStateFlow<LanguageDetector.DetectedLanguage?>(null)
    val lastDetectedLanguage: StateFlow<LanguageDetector.DetectedLanguage?> = _lastDetectedLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.userLanguageCodeFlow.collect { _userLanguageCode.value = it }
        }
        viewModelScope.launch {
            dataStoreManager.userLanguageNameFlow.collect { _userLanguageName.value = it }
        }
    }

    private val _isTranscribingVoice = MutableStateFlow(false)
    val isTranscribingVoice: StateFlow<Boolean> = _isTranscribingVoice.asStateFlow()

    /**
     * Transcribe audio using Sarvam AI Indic STT and automatically query AI.
     */
    fun transcribeAndSendVoice(audioFile: java.io.File, onTranscribed: (String) -> Unit) {
        viewModelScope.launch {
            _isTranscribingVoice.value = true
            _uiState.value = ChatUiState.Loading
            try {
                val transcript = repository.transcribeAudioWithSarvam(audioFile, _userLanguageCode.value)
                if (transcript.isNotBlank()) {
                    onTranscribed(transcript)
                    sendTextMessage(transcript, isVoiceInput = true)
                } else {
                    _uiState.value = ChatUiState.Error("Could not detect clear speech. Please try again.")
                }
            } catch (e: Exception) {
                android.util.Log.e("ChatVM", "Sarvam STT failed: ${e.message}")
                if (e.message == "DAILY_SARVAM_LIMIT_REACHED" || e.cause?.message == "DAILY_SARVAM_LIMIT_REACHED") {
                    val limitMsg = when (_userLanguageCode.value.lowercase()) {
                        "hi" -> "दैनिक सर्वम AI वॉयस सीमा समाप्त (आज 2/2 प्रश्न प्रयुक्त)। कृपया अपना प्रश्न टाइप करें।"
                        "mr" -> "दैनिक सर्वम AI व्हॉइस मर्यादा संपली (आज 2/2 प्रश्न वापरले). कृपया आपला प्रश्न टाईप करा."
                        "bn" -> "দৈনিক সর্বম AI ভয়েস সীমা সমাপ্ত (আজ ২/২ প্রশ্ন ব্যবহৃত)। দয়া করে আপনার প্রশ্নটি টাইপ করুন।"
                        "te" -> "రోజువారీ సర్వం AI వాయిస్ పరిమితి ముగిసింది (ఈరోజు 2/2 ప్రశ్నలు ఉపయోగించబడ్డాయి). దయచేసి టైప్ చేయండి."
                        "ta" -> "தினசரி சர்வம் AI குரல் வரம்பு முடிந்தது (இன்று 2/2 பயன்படுத்தப்பட்டது). தட்டச்சு செய்யவும்."
                        else -> "Daily Sarvam AI voice limit reached (2/2 queries used today). Please type your query."
                    }
                    _uiState.value = ChatUiState.Error(limitMsg)
                } else {
                    _uiState.value = ChatUiState.Error("Sarvam AI STT connection failed: ${e.localizedMessage ?: "Please try again."}")
                }
            } finally {
                _isTranscribingVoice.value = false
                try { audioFile.delete() } catch (_: Exception) {}
            }
        }
    }

    /**
     * Send user message (voice transcription or typed text).
     * Automatically identifies the input language and enforces response in that exact same language.
     */
    fun sendTextMessage(text: String, isVoiceInput: Boolean = false) {
        if (text.isBlank()) return
        viewModelScope.launch {
            ensureChatCreated(text)
            _uiState.value = ChatUiState.Loading

            // Auto-detect language of the input query
            val detected = LanguageDetector.detectLanguage(text)
            _lastDetectedLanguage.value = detected

            val targetLangName = if (detected.code != "en") detected.name else _userLanguageName.value
            val targetLangCode = if (detected.code != "en") detected.code else _userLanguageCode.value

            try {
                repository.sendTextMessage(
                    chatId = currentChatId,
                    userQuery = text,
                    targetLanguage = targetLangName,
                    langCode = targetLangCode
                )
                _uiState.value = ChatUiState.Success(currentChatId)
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.localizedMessage ?: "Failed to get response")
            }
        }
    }

    fun sendImageQuery(base64Image: String, captionText: String) {
        viewModelScope.launch {
            val queryText = captionText.ifBlank { "Analyze crop disease in this photo" }
            ensureChatCreated(queryText)
            _uiState.value = ChatUiState.Loading

            val detected = LanguageDetector.detectLanguage(queryText)
            _lastDetectedLanguage.value = detected

            val targetLangName = if (detected.code != "en") detected.name else _userLanguageName.value
            val targetLangCode = if (detected.code != "en") detected.code else _userLanguageCode.value

            try {
                repository.analyzeCropAndGetAdvisory(
                    chatId = currentChatId,
                    base64Image = base64Image,
                    userQuery = queryText,
                    targetLanguage = targetLangName,
                    langCode = targetLangCode
                )
                _uiState.value = ChatUiState.Success(currentChatId)
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.localizedMessage ?: "Crop health analysis failed")
            }
        }
    }

    private suspend fun ensureChatCreated(initialQuery: String) {
        if (!isNewChatCreated && existingChatId == null) {
            repository.createNewChat(currentChatId, initialQuery)
            isNewChatCreated = true
        }
    }

    /**
     * Reads aloud the message in the exact language the message is written in.
     */
    fun toggleTts(messageId: String, text: String) {
        val detected = LanguageDetector.detectLanguage(text)
        val speakLangCode = if (detected.code != "en") detected.code else _userLanguageCode.value
        ttsManager.speak(messageId, text, speakLangCode)
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }
}
