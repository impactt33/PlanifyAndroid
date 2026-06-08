package com.example.planify.main.navigation.screens.notifications_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.services.ActionsService
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteStatusUpdatedSchema
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
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor(
    private val actionService: ActionsService,
    private val meetingsService: MeetingsService,
    private val profileService: ProfilesService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationState.empty())
    val uiState = _uiState.asStateFlow()

    private val inFlight: MutableSet<String> = ConcurrentHashMap.newKeySet()

    init {
        viewModelScope.launch {
            actionService.observeActions().collect { list -> reconcile(list) }
        }
    }

    private fun reconcile(list: List<Action<*>>) {
        list.forEach { action ->
            if (_uiState.value.actions.containsKey(action.id)) return@forEach
            if (!inFlight.add(action.id)) return@forEach

            when (action.type) {
                "meetings:invited" -> {
                    val data = action.data as? UserActionInvitedToMeetingSchema
                    if (data == null) { inFlight.remove(action.id); return@forEach }
                    viewModelScope.launch { processMeetingInvite(action.id, data) }
                }

                "meetings:invite_status_updated" -> {
                    val data = action.data as? UserActionInviteStatusUpdatedSchema
                    if (data == null) { inFlight.remove(action.id); return@forEach }
                    viewModelScope.launch { processMeetingStatusUpdated(action.id, data) }
                }

                else -> inFlight.remove(action.id)
            }
        }

        val presentIds = list.mapTo(HashSet()) { it.id }
        val stale = _uiState.value.actions.keys - presentIds
        if (stale.isNotEmpty()) {
            _uiState.update { state -> state.copy(actions = state.actions - stale) }
            stale.forEach { inFlight.remove(it) }
        }
    }

    private suspend fun processMeetingStatusUpdated(actionId: String, data: UserActionInviteStatusUpdatedSchema) {
        _uiState.update { it.copy(actions = it.actions + (actionId to ResourceState.Loading)) }

        coroutineScope {
            val profileDeferred = async { profileService.fetchProfileById(data.targetId) }
            val contextDeferred = async { meetingsService.fetchMeetingContext(data.meetingId) }

            val profile = profileDeferred.await().onFailure { Log.e("NotifVM", "profile failed: $it") }.getOrElse { null }
            val context = contextDeferred.await().onFailure { Log.e("NotifVM", "context failed: $it") }.getOrElse { null }

            if (profile != null && context != null) {
                _uiState.update {
                    it.copy(
                        actions = it.actions + (actionId to ResourceState.Success(
                            NotificationAction.NotificationStatusUpdate(profile, context)
                        ))
                    )
                }
            } else {
                _uiState.update { it.copy(actions = it.actions - actionId) }
                inFlight.remove(actionId)
            }
        }
    }

    private suspend fun processMeetingInvite(actionId: String, data: UserActionInvitedToMeetingSchema) {
        _uiState.update { it.copy(actions = it.actions + (actionId to ResourceState.Loading)) }

        coroutineScope {
            val profileDeferred = async { profileService.fetchProfileById(data.senderId) }
            val contextDeferred = async { meetingsService.fetchMeetingContext(data.meetingId) }

            val profile = profileDeferred.await().onFailure { Log.e("NotifVM", "profile failed: $it") }.getOrElse { null }
            val context = contextDeferred.await().onFailure { Log.e("NotifVM", "context failed: $it") }.getOrElse { null }

            if (profile != null && context != null) {
                _uiState.update {
                    it.copy(
                        actions = it.actions + (actionId to ResourceState.Success(
                            NotificationAction.NotificationInvite(profile, context)
                        ))
                    )
                }
            } else {
                _uiState.update { it.copy(actions = it.actions - actionId) }
                inFlight.remove(actionId)
            }
        }
    }
}