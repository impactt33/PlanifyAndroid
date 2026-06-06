package com.example.planify.main.features.meetings.domain.use_cases

import com.example.planify.core.notifications.data.Notifier
import com.example.planify.core.notifications.domain.AppNotification
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.meetings.domain.entities.MeetingInviteStatus
import com.example.planify.main.features.meetings.domain.notifications.MeetingNotifications
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteRescheduleRequestedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteStatusUpdatedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInvitedToMeetingSchema
import javax.inject.Inject

class NotifyNewMessageUseCase @Inject constructor(
    private val notifier: Notifier
) {
    operator fun invoke(action: Action<*>) {
        val content = action.toContent() ?: return

        notifier.show(
            AppNotification(
                id = action.id.hashCode(),
                channelId = MeetingNotifications.CHANNEL_ID,
                title = content.title,
                body = content.body,
                deepLink = MeetingNotifications.notificationDeepLink(action.id)
            )
        )
    }

    private fun Action<*>.toContent(): Content? = when (type) {
        "meetings:invited" -> (data as? UserActionInvitedToMeetingSchema)?.let {
            Content(
                title = "Новое приглашение",
                body = "Пользователь ${it.senderId} пригласил вас на встречу"
            )
        }
        "meetings:invite_reschedule_requested" -> (data as? UserActionInviteRescheduleRequestedSchema)?.let {
            Content(
                title = "Предложение перенести встречу",
                body = "Пользователь ${it.senderId} предложил перенести встречу на ${it.rescheduleTo}"
            )
        }
        "meetings:invite_status_updated" -> (data as? UserActionInviteStatusUpdatedSchema)?.let {
            val actionFromUser: String = when(data.newStatus) {
                MeetingInviteStatus.ACCEPTED -> "принял(а)"
                MeetingInviteStatus.REJECTED -> "отклонил(а)"
                MeetingInviteStatus.RESCHEDULE_REQUESTED -> " "
                else -> " "
            }

            Content(
                title = "Статус приглашения обновлен",
                body = "Пользователь ${data.targetId} $actionFromUser предложение о встрече ${data.meetingId}"
            )
        }
        else -> null
    }

    private data class Content(
        val title: String,
        val body: String
    )
}