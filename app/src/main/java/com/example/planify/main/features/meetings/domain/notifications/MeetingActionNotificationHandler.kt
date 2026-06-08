package com.example.planify.main.features.meetings.domain.notifications

import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.notifications.ActionNotificationHandler
import com.example.planify.main.features.meetings.domain.use_cases.NotifyActionUseCase
import jakarta.inject.Inject

class MeetingActionNotificationHandler @Inject constructor(
    private val notifyAction: NotifyActionUseCase
) : ActionNotificationHandler {
    override val supportedTypes = setOf(
        "meetings:invited",
        "meetings:invite_reschedule_requested",
        "meetings:invite_reschedule_responded",
        "meetings:invite_status_updated",
        "meetings:meeting:invited",
        "meetings:meeting:new_participant",
        "meetings:meeting:invite_status_updated",
        "meetings:meeting:invite_reschedule_requested",
        "meetings:meeting:invite_reschedule_responded",
    )

    override suspend fun handle(action: Action<*>) {
        notifyAction(action)
    }
}