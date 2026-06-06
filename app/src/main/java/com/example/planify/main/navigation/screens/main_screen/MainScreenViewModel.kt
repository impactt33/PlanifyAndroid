package com.example.planify.main.navigation.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.actions.data.sources.ActionsLocalDataSource
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.notifications.ActionNotificationHandler
import com.example.planify.main.features.actions.domain.services.ActionsService
import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import com.example.planify.main.features.firebase_fcm.domain.services.FcmService
import com.example.planify.main.features.meetings.domain.notifications.MeetingActionNotificationHandler
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteRescheduleRequestedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInvitedToMeetingSchema
import com.example.planify.main.features.settings.domain.services.SettingsService
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val actionsService: ActionsService,
    private val handlers: Set<@JvmSuppressWildcards ActionNotificationHandler>,
    private val actionsLocalDataSource: ActionsLocalDataSource,
    private val fcmService: FcmService,
    private val settingsService: SettingsService
) : ViewModel() {
    private val _isFirstStartState = MutableStateFlow(true)

    val isFirstStart = _isFirstStartState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsService.settingsFlow.collect { settings ->
                _isFirstStartState.emit(settings.isFirstStart)
            }
        }

        viewModelScope.launch {
            actionsService.actionsFlow.collect { action ->
                if (actionsLocalDataSource.markActionNotifiedIfNewer(action.id)) {
                    handlers.find { action.type in it.supportedTypes }?.handle(action)
                }
            }
        }

        viewModelScope.launch {
            handlers.find { "meetings:invite_reschedule_requested" in it.supportedTypes }?.handle(
                Action(
                    id = "test-${System.currentTimeMillis()}-0",
                    type = "meetings:invite_reschedule_requested",
                    data = UserActionInviteRescheduleRequestedSchema(
                        senderId = 123L,
                        targetId = 124L,
                        meetingId = 125,
                        inviteUuid = "1234",
                        updatedAt = LocalDateTime.now(),
                        rescheduleTo = LocalDateTime.now()
                    )
                )
            )
        }
    }

    fun sendFcmToken() {
        viewModelScope.launch {
            val currentToken = FirebaseMessaging.getInstance().token.await()

            Log.d("FCM TOKEN FROM VM", currentToken)
            fcmService.sendFcmToken(currentToken)
        }
    }

    fun setIsFirstStartFalse() {
        viewModelScope.launch {
            _isFirstStartState.emit(false)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun onAction(action: Action<*>) {
        Log.i("Actions", "${action.type}: ${action.id}")
    }
}
