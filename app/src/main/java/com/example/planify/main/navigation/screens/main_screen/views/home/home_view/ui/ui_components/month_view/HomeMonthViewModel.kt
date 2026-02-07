package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.month_view

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.week_view.WeekUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeMonthViewModel @Inject constructor(
    private val meetingService: MeetingsService
): ViewModel() {
    private val _monthUiState = MutableStateFlow<MonthUIState>(MonthUIState.Loading)
    val monthUiState = _monthUiState.asStateFlow()

    fun fetchMeetingsShort(
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        viewModelScope.launch {
            _monthUiState.emit(MonthUIState.Loading)

            meetingService.fetchMyDailyMeetingsShort(
                startDate = startDate,
                endDate = endDate
            )
                .onSuccess { map ->
                    _monthUiState.emit(MonthUIState.ContentData(map))
                }
                .onFailure { error ->
                    _monthUiState.emit(MonthUIState.Error(error.message ?: "Runtime error"))
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
