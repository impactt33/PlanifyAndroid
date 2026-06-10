package com.example.planify.main.features.meetings.data.dto

import com.example.planify.main.features.meetings.domain.entities.MeetingInvitationContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMeetingInvitationsContextDTO (
    @SerialName("invites")
    val meetingInvitations: List<MeetingInvitationContextDTO>
) {
    fun toEntity(): List<MeetingInvitationContext> = meetingInvitations.map { it.toEntity() }
}