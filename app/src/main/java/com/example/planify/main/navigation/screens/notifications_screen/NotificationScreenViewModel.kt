package com.example.planify.main.navigation.screens.notifications_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.services.ActionsService
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInvitedToMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor(
    private val actionService: ActionsService,
    private val meetingsService: MeetingsService,
    private val profileService: ProfilesService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationState.empty())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val local = actionService.getAllActionLocal().getOrDefault(emptyList())
            Log.d("NotifVM", "local actions: ${local.size}")
            local.forEach {
                Log.d("NotifVM", "  type=${it.type} data=${it.data?.javaClass?.simpleName}")
            }
            local.forEach { processAction(it) }
        }

        viewModelScope.launch {
            actionService.actionsFlow.collect { action ->
                Log.d("NotifVM", "flow action: ${action.type}")
                processAction(action)
            }
        }
    }

    private fun processAction(action: Action<*>) {
        if (_uiState.value.actions.containsKey(action.id)) return

        when (action.type) {
            "meetings:invited" -> {
                val data = action.data as? UserActionInvitedToMeetingSchema ?: return
                viewModelScope.launch { processMeetingInvite(action.id, data) }
            }
            "meetings:invite_reschedule_requested" -> {
                // TODO
            }
        }
    }

    private suspend fun processMeetingInvite(actionId: String, data: UserActionInvitedToMeetingSchema) {
        Log.d("NotifVM", "processMeetingInvite: $actionId")
        _uiState.update { it.copy(actions = it.actions + (actionId to ResourceState.Loading)) }

        coroutineScope {
            val profileDeferred = async { profileService.fetchProfileById(data.senderId) }
            val contextDeferred = async { meetingsService.fetchMeetingContext(data.meetingId) }

            val profile = profileDeferred.await()
                .onFailure { Log.e("NotifVM", "profile failed: $it") }
                .getOrElse { null }
            val context = contextDeferred.await()
                .onFailure { Log.e("NotifVM", "context failed: $it") }
                .getOrElse { null }

            Log.d("NotifVM", "profile=$profile context=$context")

            if (profile != null && context != null) {
                _uiState.update {
                    it.copy(actions = it.actions + (actionId to ResourceState.Success(
                        NotificationAction.NotificationInvite(profile, context)
                    )))
                }
            } else {
                _uiState.update { it.copy(actions = it.actions - actionId) }
            }
        }
    }
}