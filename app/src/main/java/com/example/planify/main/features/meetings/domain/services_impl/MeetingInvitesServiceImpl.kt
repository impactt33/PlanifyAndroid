package com.example.planify.main.features.meetings.domain.services_impl

import com.example.planify.main.features.meetings.domain.entities.MeetingInvite
import com.example.planify.main.features.meetings.domain.repositories.MeetingInvitesRepository
import com.example.planify.main.features.meetings.domain.services.MeetingInvitesService
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingInvitesServiceImpl @Inject constructor(
    private val meetingInvitesRepository: MeetingInvitesRepository
): MeetingInvitesService {
    override suspend fun inviteUser(meetingId: Long, targetUserId: Long): Result<MeetingInvite> {
        return meetingInvitesRepository.inviteUser(meetingId = meetingId, targetUserId = targetUserId)
    }

    override suspend fun inviteRescheduleResponse(inviteUuid: String, shouldReschedule: Boolean): Result<Unit> {
        return meetingInvitesRepository.inviteRescheduleResponse(inviteUuid = inviteUuid, shouldReschedule = shouldReschedule)
    }

    override suspend fun inviteRescheduleRequest(inviteUuid: String, rescheduleTo: LocalDateTime): Result<Unit> {
        return meetingInvitesRepository.inviteRescheduleRequest(inviteUuid = inviteUuid, rescheduleTo = rescheduleTo)
    }

    override suspend fun inviteReject(inviteUuid: String): Result<Unit> {
        return meetingInvitesRepository.inviteReject(inviteUuid = inviteUuid)
    }

    override suspend fun inviteAccept(inviteUuid: String): Result<Unit> {
        return meetingInvitesRepository.inviteAccept(inviteUuid = inviteUuid)
    }

    override suspend fun getInvite(inviteUuid: String): Result<MeetingInvite> {
        return meetingInvitesRepository.getInvite(inviteUuid = inviteUuid)
    }

}