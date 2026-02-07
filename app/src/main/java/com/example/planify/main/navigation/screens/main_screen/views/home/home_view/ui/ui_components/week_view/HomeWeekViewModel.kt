package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.week_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HomeWeekViewModel @Inject constructor(
    private val meetingService: MeetingsService
): ViewModel() {
    private val _weekUiState = MutableStateFlow<WeekUIState>(WeekUIState.Loading)
    val uiState = _weekUiState.asStateFlow()

    fun fetchMeetingsShort(
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        viewModelScope.launch {
            _weekUiState.emit(WeekUIState.Loading)

            meetingService.fetchMyDailyMeetingsShort(
                startDate = startDate,
                endDate = endDate
            )
                .onSuccess { map ->
                    _weekUiState.emit(WeekUIState.ContentData(map))
                }
                .onFailure { error ->
                    _weekUiState.emit(WeekUIState.Error(error.message ?: "Runtime error"))
                }
        }
    }

    init {
        fetchMeetingsShort(
            LocalDate.now().minusMonths(6.toLong()),
            LocalDate.now().plusMonths(6.toLong())
        )
    }
}