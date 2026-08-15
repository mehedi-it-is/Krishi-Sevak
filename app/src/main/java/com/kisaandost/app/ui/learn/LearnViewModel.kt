package com.kisaandost.app.ui.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisaandost.app.data.engine.FarmingGuideTip
import com.kisaandost.app.data.engine.LearnFarmingData
import com.kisaandost.app.data.local.datastore.DataStoreManager
import com.kisaandost.app.utils.TtsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LearnViewModel(
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager
) : ViewModel() {

    val userLanguageCode: StateFlow<String> = dataStoreManager.userLanguageCodeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    val tipsList: StateFlow<List<FarmingGuideTip>> = userLanguageCode.map { lang ->
        LearnFarmingData.getTipsForLanguage(lang)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LearnFarmingData.getTipsForLanguage("en"))

    fun toggleTts(id: String, text: String) {
        viewModelScope.launch {
            ttsManager.speak(id, text, userLanguageCode.value)
        }
    }
}
