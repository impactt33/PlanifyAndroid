package com.example.planify.main.navigation.screens.meeting_info_screen

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.meetings.domain.schemas.PatchMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MeetingInfoViewModel @Inject constructor(
    private val meetingService: MeetingsService,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val meetingId: Long = checkNotNull(savedStateHandle[AppRoute.MeetingInfoMenu.ARG])

    val authFlow = authService.authStateFlow

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<MeetingInfoEvent>()

    val eventFlow = _eventFlow.asSharedFlow()

    fun fetchMeetingContext(meetingId: Long, refresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.emit(if (refresh) UIState.Refreshing else UIState.Loading)

            meetingService.fetchMeetingContext(meetingId = meetingId)
                .onSuccess { meetingContext ->
                    _uiState.emit(
                        UIState.ContentData(meetingContext)
                    )
                }
                .onFailure { error ->
                    _uiState.emit(UIState.Error(error.message ?: "Runtime error"))
                }
        }
    }

    fun runFetchMeetingContext(meetingId: Long, refresh: Boolean = false) {
        viewModelScope.launch {
            fetchMeetingContext(meetingId, refresh = refresh)
        }
    }

    fun rescheduleThisMeeting(newStartTime: LocalDateTime, newDuration: Int) {
        val patch = PatchMeetingSchema(
            startsAt = newStartTime,
            duration = newDuration
        )

        viewModelScope.launch {
            meetingService.patchMeeting(meetingId, patch)
                .onSuccess {
                    fetchMeetingContext(meetingId, true)
                }
                .onFailure { error ->
                    _eventFlow.emit(
                        MeetingInfoEvent.ShowToast(
                            error.message ?: "Не удалось изменить встречу"
                        )
                    )
                }
        }
    }

    init {
        fetchMeetingContext(meetingId)
    }
}