package com.example.planify.main.navigation.screens.main_screen.views.home.home_view

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.meeting.domain.services.MeetingService
import com.example.planify.main.features.meeting.domain.services_impl.MeetingServiceImplST
import com.example.planify.main.features.meeting.entities.MeetingInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

@Suppress("DEPRECATION")
class HomeViewModel(
    val meetingService: MeetingService
) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentWeekOffset = MutableStateFlow(0)
    val currentWeekOffset: StateFlow<Int> = _currentWeekOffset.asStateFlow()
    private val monthFormatter = DateTimeFormatter.ofPattern(
        "LLLL yyyy", Locale("ru")
    )

    fun getMeetingsInfo() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            runCatching { meetingService.getMeetingsInfo() }
                .onSuccess { map ->
                    _uiState.value = UIState.ContentData(map)
                }
                .onFailure { error ->
                    _uiState.value = UIState.Error(error.message ?: "Runtime error")
                }
        }
    }

    fun getMeetingsInfoByDate(date: LocalDate): List<MeetingInfo> {
        return if (_uiState.value is UIState.ContentData) {
            ((_uiState.value as UIState.ContentData).meetingsInfo[date] ?: emptyList())
                .sortedBy { it.meeting.timeStart }
        } else {
            emptyList()
        }
    }

    fun onWeekChanged(offset: Int) {
        _currentWeekOffset.value = offset
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getMonthTitle(offset: Int): String {
        val monthTitle = LocalDate.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .plusWeeks(offset.toLong())

        return monthTitle.format(monthFormatter)
            .replaceFirstChar { it.uppercase() }
    }
}