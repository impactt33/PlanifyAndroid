package com.example.planify.main.features.meetings.data.dto

import com.example.planify.main.features.meetings.domain.entities.MeetingInvite
import com.example.planify.main.features.meetings.domain.entities.MeetingInviteStatus
import java.time.LocalDateTime

data class MeetingInviteDTO(
    val uuid: String,
    val meetingId: Long,
    val senderId: Long,
    val targetUserId: Long,
    val status: MeetingInviteStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    fun toEntity(): MeetingInvite = MeetingInvite(
        uuid = uuid,
        meetingId = meetingId,
        senderId = senderId,
        targetUserId = targetUserId,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
