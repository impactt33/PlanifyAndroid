package com.example.planify.main.features.meetings.domain.services_impl

import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import com.example.planify.main.features.actions.domain.utils.registerSchema
import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.repositories.MeetingsRepository
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.schemas.PatchMeetingSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionInviteRescheduleRequestedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionInviteRescheduleRespondedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionInviteStatusUpdatedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionNewParticipantSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.MeetingActionUserInvitedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteRescheduleRequestedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteRescheduleRespondedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInviteStatusUpdatedSchema
import com.example.planify.main.features.meetings.domain.schemas.actions.UserActionInvitedToMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingService
import java.time.LocalDate
import javax.inject.Inject

class MeetingServiceImpl @Inject constructor(
    private val meetingsRepository: MeetingsRepository,
    actionDataParser: ActionDataParser
) : MeetingService {
    init {
        actionDataParser.registerSchema<MeetingActionInviteRescheduleRequestedSchema>("meetings:invite_reschedule_requested")
        actionDataParser.registerSchema<MeetingActionInviteRescheduleRespondedSchema>("meetings:invite_reschedule_responded")
        actionDataParser.registerSchema<MeetingActionInviteStatusUpdatedSchema>("meetings:invite_status_updated")
        actionDataParser.registerSchema<MeetingActionNewParticipantSchema>("meetings:new_participant")
        actionDataParser.registerSchema<MeetingActionUserInvitedSchema>("meetings:invited")
        actionDataParser.registerSchema<UserActionInvitedToMeetingSchema>("meetings:invited")
        actionDataParser.registerSchema<UserActionInviteRescheduleRequestedSchema>("meetings:invite_reschedule_requested")
        actionDataParser.registerSchema<UserActionInviteRescheduleRespondedSchema>("meetings:invite_reschedule_responded")
        actionDataParser.registerSchema<UserActionInviteStatusUpdatedSchema>("meetings:invite_status_updated")
    }

    override suspend fun createMeeting(schema: CreateMeetingSchema): Result<Meeting> {
        return meetingsRepository.createMeeting(schema)
    }

    override suspend fun getMeeting(meetingId: Long): Result<Meeting> {
        return meetingsRepository.getMeeting(meetingId)
    }

    override suspend fun patchMeeting(meetingId: Long, patch: PatchMeetingSchema): Result<Unit> {
        return meetingsRepository.patchMeeting(meetingId, patch)
    }

    override suspend fun fetchMyDailyMeetings(
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<Map<LocalDate, List<MeetingContext>>> {
        return meetingsRepository.fetchMyDailyMeetings(startDate, endDate)
    }

    override suspend fun fetchMyDailyMeetingsShort(startDate: LocalDate, endDate: LocalDate): Result<Map<LocalDate, Int>> {
        return meetingsRepository.fetchMyDailyMeetingsShort(startDate, endDate)
    }
}
