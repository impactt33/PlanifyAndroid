package com.example.planify.main.navigation.screens.create_meeting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingInvitesService
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    val meetingsService: MeetingsService,
    val meetingInvitesService: MeetingInvitesService
): ViewModel() {

    private val _navigation = MutableSharedFlow<AppRoute>()
    val navigation = _navigation.asSharedFlow()

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Creating)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun createMeeting(schema: CreateMeetingSchema) {
        viewModelScope.launch {
            meetingsService.createMeeting(schema = schema)
                .onSuccess {
                    _navigation.emit(AppRoute.MeetingInfoMenu)
                }
                .onFailure { error ->
                    _uiState.emit(UIState.Error(error.message ?: "Runtime error"))
                }
        }
    }

}