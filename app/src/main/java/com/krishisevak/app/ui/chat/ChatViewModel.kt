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

    val userLanguageCode: StateFlow<String> = dataStoreManager.userLanguageCodeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    val userLanguageName: StateFlow<String> = dataStoreManager.userLanguageNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "English")

    private val _lastDetectedLanguage = MutableStateFlow<LanguageDetector.DetectedLanguage?>(null)
    val lastDetectedLanguage: StateFlow<LanguageDetector.DetectedLanguage?> = _lastDetectedLanguage.asStateFlow()

    private val _isTranscribingVoice = MutableStateFlow(false)
    val isTranscribingVoice: StateFlow<Boolean> = _isTranscribingVoice.asStateFlow()

    private val _translatingMessageIds = MutableStateFlow<Set<String>>(emptySet())
    val translatingMessageIds: StateFlow<Set<String>> = _translatingMessageIds.asStateFlow()

    private val _translatedMessages = MutableStateFlow<Map<String, String>>(emptyMap())
    val translatedMessages: StateFlow<Map<String, String>> = _translatedMessages.asStateFlow()

    fun setLanguage(code: String, name: String) {
        viewModelScope.launch {
            dataStoreManager.updateLanguage(code, name)
        }
    }

    fun translateMessage(messageId: String, text: String, targetLangCode: String, targetLangName: String) {
        if (_translatingMessageIds.value.contains(messageId)) return
        viewModelScope.launch {
            _translatingMessageIds.value = _translatingMessageIds.value + messageId
            try {
                val translated = repository.translateText(text, targetLangCode, targetLangName)
                _translatedMessages.value = _translatedMessages.value + (messageId to translated)
            } catch (e: Exception) {
                android.util.Log.e("ChatVM", "Translation error: ${e.message}")
            } finally {
                _translatingMessageIds.value = _translatingMessageIds.value - messageId
            }
        }
    }

    /**
     * Transcribe audio using Sarvam AI Indic STT (saaras:v3) and automatically query AI (sarvam-30b) & auto-speak (bulbul:v3).
     */
    fun transcribeAndSendVoice(audioFile: java.io.File, onTranscribed: (String) -> Unit) {
        viewModelScope.launch {
            _isTranscribingVoice.value = true
            _uiState.value = ChatUiState.Loading
            try {
                val transcript = repository.transcribeAudioWithSarvam(audioFile, userLanguageCode.value)
                if (transcript.isNotBlank()) {
                    onTranscribed(transcript)
                    sendTextMessage(transcript, isVoiceInput = true, autoSpeak = true)
                } else {
                    _uiState.value = ChatUiState.Error("Could not detect clear speech. Please try again.")
                }
            } catch (e: Exception) {
                android.util.Log.e("ChatVM", "Sarvam saaras:v3 STT failed: ${e.message}")
                if (e.message == "DAILY_SARVAM_LIMIT_REACHED" || e.cause?.message == "DAILY_SARVAM_LIMIT_REACHED") {
                    val limitMsg = com.krishisevak.app.utils.AppStrings.get("daily_query_limit_exhausted", userLanguageCode.value)
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
     * If autoSpeak is true (voice query), automatically speaks the response with bulbul:v3.
     */
    fun sendTextMessage(text: String, isVoiceInput: Boolean = false, autoSpeak: Boolean = false) {
        if (text.isBlank()) return
        viewModelScope.launch {
            ensureChatCreated(text)
            _uiState.value = ChatUiState.Loading

            // Auto-detect language of the input query
            val detected = LanguageDetector.detectLanguage(text)
            _lastDetectedLanguage.value = detected

            val targetLangCode = when {
                detected.code != "en" -> detected.code
                userLanguageCode.value.isNotBlank() -> userLanguageCode.value
                else -> "en"
            }
            val targetLangName = when {
                detected.code != "en" -> detected.name
                userLanguageName.value.isNotBlank() -> userLanguageName.value
                else -> "English"
            }

            try {
                val (responseText, aiMsgId) = repository.sendTextMessage(
                    chatId = currentChatId,
                    userQuery = text,
                    targetLanguage = targetLangName,
                    langCode = targetLangCode
                )
                _uiState.value = ChatUiState.Success(currentChatId)

                // Instantly speak the AI response with Sarvam bulbul:v3
                ttsManager.speak(aiMsgId, responseText, targetLangCode)
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

            val targetLangCode = when {
                detected.code != "en" -> detected.code
                userLanguageCode.value.isNotBlank() -> userLanguageCode.value
                else -> "en"
            }
            val targetLangName = when {
                detected.code != "en" -> detected.name
                userLanguageName.value.isNotBlank() -> userLanguageName.value
                else -> "English"
            }

            try {
                val (advisoryText, aiMsgId) = repository.analyzeCropAndGetAdvisory(
                    chatId = currentChatId,
                    base64Image = base64Image,
                    userQuery = queryText,
                    targetLanguage = targetLangName,
                    langCode = targetLangCode
                )
                _uiState.value = ChatUiState.Success(currentChatId)

                // Instantly speak the crop advisory with Sarvam bulbul:v3
                ttsManager.speak(aiMsgId, advisoryText, targetLangCode)
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
        val speakLangCode = if (detected.code != "en") detected.code else userLanguageCode.value
        ttsManager.speak(messageId, text, speakLangCode)
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }
}
