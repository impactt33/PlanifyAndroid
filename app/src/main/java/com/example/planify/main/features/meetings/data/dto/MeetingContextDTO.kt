package com.example.planify.main.features.meetings.data.dto

import com.example.planify.main.common.dto.ProfileDTO
import com.example.planify.main.features.meetings.domain.entities.MeetingContext

data class MeetingContextDTO(
    val participantProfiles: List<ProfileDTO>,
    val invites: List<MeetingInviteDTO>,
    val meeting: MeetingDTO
) {
    fun toEntity(): MeetingContext = MeetingContext(
        participantProfiles = participantProfiles.map { it.toEntity() },
        invites = invites.map { it.toEntity() },
        meeting = meeting.toEntity(),
    )
}
