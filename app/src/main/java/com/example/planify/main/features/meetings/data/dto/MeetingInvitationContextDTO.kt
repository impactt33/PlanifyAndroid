package com.example.planify.main.features.meetings.data.dto

import com.example.planify.main.common.dto.ProfileDTO
import com.example.planify.main.features.meetings.domain.entities.MeetingInvitationContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeetingInvitationContextDTO(
    @SerialName("invite")
    val invite: MeetingInviteDTO,
    @SerialName("meeting")
    val meeting: MeetingDTO,
    @SerialName("senderProfile")
    val senderProfile: ProfileDTO,
    @SerialName("targetProfile")
    val targetProfile: ProfileDTO
) {
    fun toEntity(): MeetingInvitationContext = MeetingInvitationContext(
        invite = this.invite.toEntity(),
        meeting = this.meeting.toEntity(),
        senderProfile = this.senderProfile.toEntity(),
        targetProfile = this.targetProfile.toEntity()
    )
}