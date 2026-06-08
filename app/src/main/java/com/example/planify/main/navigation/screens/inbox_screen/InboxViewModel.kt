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
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val actionService: ActionsService,
    private val meetingsService: MeetingsService,
    private val meetingInvitesService: MeetingInvitesService
) : ViewModel() {
    private val _uiState = MutableStateFlow(InboxState.empty())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableStateFlow<List<Action<*>>>(emptyList())
    val actions = _actions.asStateFlow()

    private val inFlight: MutableSet<String> = ConcurrentHashMap.newKeySet()

    init {
        viewModelScope.launch {
            actionService.observeActions().collect { list -> reconcile(list) }
        }
    }

    private fun reconcile(list: List<Action<*>>) {
        _actions.value = list

        list.forEach { action ->
            if (action.type != "meetings:invited") return@forEach
            val data = action.data as? UserActionInvitedToMeetingSchema ?: return@forEach

            if (_uiState.value.actions.containsKey(action.id)) return@forEach
            if (!inFlight.add(action.id)) return@forEach

            viewModelScope.launch {
                val ok = processMeetingInvite(action.id, data)
                if (!ok) inFlight.remove(action.id)
            }
        }

        val presentIds = list.mapTo(HashSet()) { it.id }
        val stale = _uiState.value.actions.keys - presentIds
        if (stale.isNotEmpty()) {
            _uiState.update { state -> state.copy(actions = state.actions - stale) }
            stale.forEach { inFlight.remove(it) }
        }
    }

    private suspend fun processMeetingInvite(
        actionId: String,
        data: UserActionInvitedToMeetingSchema
    ): Boolean {
        _uiState.update { it.copy(actions = it.actions + (actionId to ResourceState.Loading)) }

        return fetchMeetingContext(data.meetingId)
            .onSuccess { context ->
                _uiState.update {
                    it.copy(
                        actions = it.actions + (actionId to ResourceState.Success(
                            InboxAction.Invite(
                                meetingContext = context,
                                inviteUuid = data.inviteUuid,
                                actionId = actionId
                            )
                        ))
                    )
                }
            }
            .onFailure {
                _uiState.update { it.copy(actions = it.actions - actionId) }
            }
            .isSuccess
    }

    private suspend fun fetchMeetingContext(meetingId: Long): Result<MeetingContext> =
        meetingsService.fetchMeetingContext(meetingId)

    fun acceptMeeting(inviteUuid: String, actionId: String) {
        viewModelScope.launch {
            meetingInvitesService.inviteAccept(inviteUuid, actionId)
                .onSuccess { _uiState.update { it.copy(actions = it.actions - actionId) } }
                .onFailure { Log.e(this::class.simpleName, "inviteAccept failed", it) }
        }
    }

    fun rejectMeeting(inviteUuid: String, actionId: String) {
        viewModelScope.launch {
            meetingInvitesService.inviteReject(inviteUuid, actionId)
                .onSuccess { _uiState.update { it.copy(actions = it.actions - actionId) } }
                .onFailure { Log.e(this::class.simpleName, "inviteReject failed", it) }
        }
    }
}