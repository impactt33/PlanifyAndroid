package com.example.planify.main.features.meetings.data.dto.create_invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InviteUserRequestDTO(
    @SerialName("meetingId")
    val meetingId: Long,
    @SerialName("targetUserId")
    val targetUserId: Long
)