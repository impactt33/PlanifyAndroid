package com.example.planify.main.features.meetings.domain.use_cases

import android.util.Log
import com.example.planify.core.notifications.data.Notifier
import com.example.planify.core.notifications.domain.AppNotification
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.meetings.domain.entities.MeetingInviteStatus
import com.example.planify.main.features.meetings.domain.notifications.MeetingNotifications
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionInviteRescheduleRequestedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionInviteRescheduleRespondedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionInviteStatusUpdatedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionNewParticipantSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionUserInvitedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteRescheduleRequestedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteRescheduleRespondedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteStatusUpdatedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInvitedToMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NotifyActionUseCase @Inject constructor(
    private val notifier: Notifier,
    private val profilesService: ProfilesService,
    private val meetingsService: MeetingsService,
) {
    suspend operator fun invoke(action: Action<*>) {
        val content = buildContent(action) ?: run {
            Log.d(TAG, "No notification content for type = ${action.type}")
            return
        }

        notifier.show(
            AppNotification(
                id = action.id.hashCode(),
                channelId = MeetingNotifications.CHANNEL_ID,
                title = content.title,
                body = content.body,
                deepLink = MeetingNotifications.meetingDeepLink(content.meetingId)
            )
        )
    }

    private suspend fun buildContent(action: Action<*>): Content? = when (action.type) {
        "meetings:invited" -> (action.data as? UserActionInvitedToMeetingSchema)?.let {
            Content(
                title = "Новое приглашение",
                body = "${userName(it.senderId)} пригласил(а) вас на «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        "meetings:invite_reschedule_requested" -> (action.data as? UserActionInviteRescheduleRequestedSchema)?.let {
            Content(
                title = "Предложение перенести встречу",
                body = "${userName(it.senderId)} предлагает перенести «${meetingName(it.meetingId)}» на ${formatDateTime(it.rescheduleTo)}",
                meetingId = it.meetingId
            )
        }

        "meetings:invite_reschedule_responded" -> (action.data as? UserActionInviteRescheduleRespondedSchema)?.let {
            val verb = if (it.shouldReschedule) "согласился(ась) перенести" else "отклонил(а) перенос"
            Content(
                title = "Ответ на перенос",
                body = "${userName(it.senderId)} $verb «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        "meetings:invite_status_updated" -> (action.data as? UserActionInviteStatusUpdatedSchema)?.let {
            Content(
                title = "Обновление приглашения",
                body = "${userName(it.targetId)} ${statusVerb(it.newStatus)} приглашение на «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        "meetings:meeting:invited" -> (action.data as? MeetingActionUserInvitedSchema)?.let {
            Content(
                title = "Новый приглашённый",
                body = "${userName(it.targetId)} приглашён(а) на «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        "meetings:meeting:new_participant" -> (action.data as? MeetingActionNewParticipantSchema)?.let {
            Content(
                title = "Новый участник",
                body = "${userName(it.newParticipantId)} присоединился(ась) к «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        "meetings:meeting:invite_status_updated" -> (action.data as? MeetingActionInviteStatusUpdatedSchema)?.let {
            Content(
                title = "Статус приглашения",
                body = "${userName(it.targetId)} ${statusVerb(it.newStatus)} приглашение на «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        "meetings:meeting:invite_reschedule_requested" -> (action.data as? MeetingActionInviteRescheduleRequestedSchema)?.let {
            Content(
                title = "Запрос на перенос",
                body = "${userName(it.senderId)} предлагает перенести «${meetingName(it.meetingId)}» на ${formatDateTime(it.rescheduleTo)}",
                meetingId = it.meetingId
            )
        }

        "meetings:meeting:invite_reschedule_responded" -> (action.data as? MeetingActionInviteRescheduleRespondedSchema)?.let {
            val verb = if (it.shouldReschedule) "согласился(ась) перенести" else "отклонил(а) перенос"
            Content(
                title = "Ответ на перенос",
                body = "${userName(it.senderId)} $verb «${meetingName(it.meetingId)}»",
                meetingId = it.meetingId
            )
        }

        else -> null
    }

    private suspend fun userName(id: Long): String =
        profilesService.fetchProfileById(id).getOrNull()
            ?.let { "${it.firstName} ${it.lastName}".trim() }
            ?.takeIf { it.isNotBlank() }
            ?: "Пользователь #$id"

    private suspend fun meetingName(meetingId: Long): String =
        meetingsService.fetchMeetingContext(meetingId).getOrNull()
            ?.meeting?.name
            ?.takeIf { it.isNotBlank() }
            ?: "встреча #$meetingId"

    private fun statusVerb(status: MeetingInviteStatus): String = when (status) {
        MeetingInviteStatus.ACCEPTED -> "принял(а)"
        MeetingInviteStatus.REJECTED -> "отклонил(а)"
        MeetingInviteStatus.RESCHEDULE_REQUESTED -> "запросил(а) перенос —"
        MeetingInviteStatus.PENDING -> "обновил(а)"
    }

    private fun formatDateTime(dateTime: LocalDateTime): String = dateTime.format(DATE_FORMAT)

    private data class Content(
        val title: String,
        val body: String,
        val meetingId: Long
    )

    private companion object {
        const val TAG = "NotifyActionUseCase"
        val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM HH:mm")
    }
}