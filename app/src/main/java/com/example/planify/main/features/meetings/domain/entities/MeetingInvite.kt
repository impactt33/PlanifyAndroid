package com.example.planify.main.features.meetings.domain.entities

import java.time.LocalDateTime

data class MeetingInvite(
    val uuid: String,
    val meetingId: Long,
    val senderId: Long,
    val targetUserId: Long,
    val status: MeetingInviteStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
