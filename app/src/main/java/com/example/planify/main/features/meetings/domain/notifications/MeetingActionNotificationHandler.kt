package com.example.planify.main.features.meetings.domain.notifications

import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.notifications.ActionNotificationHandler
import com.example.planify.main.features.meetings.domain.use_cases.NotifyNewMessageUseCase
import jakarta.inject.Inject

class MeetingActionNotificationHandler @Inject constructor(
    private val notifyMeeting: NotifyNewMessageUseCase
) : ActionNotificationHandler {
    override val supportedTypes = setOf(
        "meetings:invited",
        "meetings:invite_reschedule_requested"
    )

    override suspend fun handle(action: Action<*>) {
        notifyMeeting(action)
    }
}