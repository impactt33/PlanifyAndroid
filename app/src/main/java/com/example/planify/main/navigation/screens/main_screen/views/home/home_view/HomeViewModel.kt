package com.example.planify.main.navigation.screens.main_screen.views.home.home_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.services.MeetingService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel
class HomeViewModel @Inject constructor(
    val meetingService: MeetingService
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    fun getMeetingsInfo() {
        viewModelScope.launch {
            _uiState.emit(UIState.Loading)
            meetingService.fetchMyDailyMeetings(
                LocalDate.now(),
                LocalDate.now().plusDays(7)
            )
                .onSuccess { map ->
                    _uiState.emit(UIState.ContentData(map))
                }
                .onFailure { error ->
                    _uiState.value = UIState.Error(error.message ?: "Runtime error")
                }
        }
    }

    fun getMeetingsInfoByDate(date: LocalDate): List<MeetingContext> {
        return if (_uiState.value is UIState.ContentData) {
            ((_uiState.value as UIState.ContentData).meetingsInfo[date] ?: emptyList())
                .sortedBy { it.meeting.startsAt }
        } else {
            emptyList()
        }
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }
}

// все возвращаемые значения функций: Unit