package com.kisaandost.app.ui.almanac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisaandost.app.data.engine.CropAlmanacData
import com.kisaandost.app.data.engine.MonthAlmanac
import com.kisaandost.app.data.local.datastore.DataStoreManager
import com.kisaandost.app.utils.TtsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class AlmanacViewModel(
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager
) : ViewModel() {

    val userLanguageCode: StateFlow<String> = dataStoreManager.userLanguageCodeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    private val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH) + 1
    private val _selectedMonthIndex = MutableStateFlow(currentMonthIndex)

    val monthsList: StateFlow<List<MonthAlmanac>> = userLanguageCode.map { lang ->
        CropAlmanacData.getMonthsData(lang)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CropAlmanacData.getMonthsData("en"))

    val selectedMonth: StateFlow<MonthAlmanac> = combine(monthsList, _selectedMonthIndex) { list, idx ->
        list.find { it.monthIndex == idx } ?: list[0]
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CropAlmanacData.getMonthsData("en")[0])

    fun selectMonth(month: MonthAlmanac) {
        _selectedMonthIndex.value = month.monthIndex
    }

    fun toggleTts(id: String, text: String) {
        viewModelScope.launch {
            ttsManager.speak(id, text, userLanguageCode.value)
        }
    }
}
