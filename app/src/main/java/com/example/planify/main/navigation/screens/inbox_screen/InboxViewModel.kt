package com.example.planify.main.navigation.screens.inbox_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.services.ActionsService
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInvitedToMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingInvitesService
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val actionService: ActionsService,
    private val meetingsService: MeetingsService,
    private val meetingInvitesService: MeetingInvitesService
): ViewModel() {
    private val _uiState = MutableStateFlow(InboxState.empty())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableStateFlow<List<Action<*>>>(emptyList())
    val actions = _actions.asStateFlow()

    init {
        viewModelScope.launch {
            actionService.getAllActionLocal()
                .getOrDefault(emptyList())
                .forEach { action -> processAction(action) }
        }

        viewModelScope.launch {
            actionService.actionsFlow.collect { action ->
                processAction(action)
            }
        }
    }

    private fun processAction(action: Action<*>) {
        _actions.update { current ->
            if (current.any { it.id == action.id }) return
            current + action
        }

        viewModelScope.launch {
            when (action.type) {
                "meetings:invited" -> {
                    if (_uiState.value.actions.containsKey(action.id)) return@launch
                    val data = action.data as? UserActionInvitedToMeetingSchema ?: return@launch
                    processMeetingInvite(actionId = action.id, data = data)
                }
            }
        }
    }

    private suspend fun processMeetingInvite(
        actionId: String,
        data: UserActionInvitedToMeetingSchema
    ) {
        _uiState.update {
            it.copy(actions = it.actions.plus(actionId to ResourceState.Loading))
        }

        fetchMeetingContext(data.meetingId)
            .onSuccess { context ->
                _uiState.update { it.copy(actions = it.actions.plus(
                    actionId to ResourceState.Success(InboxAction.Invite(
                        meetingContext = context,
                        inviteUuid = data.inviteUuid,
                        actionId = actionId
                    ))
                )) }
            }
            .onFailure {
                _uiState.update {
                    it.copy(actions = it.actions.minus(actionId))
                }
            }
    }

    private suspend fun fetchMeetingContext(meetingId: Long): Result<MeetingContext> {
        return meetingsService.fetchMeetingContext(meetingId)
    }
    fun acceptMeeting(inviteUuid: String, actionId: String) {
        viewModelScope.launch {
            meetingInvitesService.inviteAccept(inviteUuid, actionId)
            _uiState.update { it.copy(actions = it.actions.minus(actionId)) }
        }
    }

    fun rejectMeeting(inviteUuid: String, actionId: String) {
        viewModelScope.launch {
            meetingInvitesService.inviteReject(inviteUuid, actionId)
            _uiState.update { it.copy(actions = it.actions.minus(actionId)) }
        }
    }
}
