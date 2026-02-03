package com.example.planify.main.features.meeting.domain.entities

import java.time.LocalDateTime

data class Invite(
    val uuid: String,
    val meetingId: Long,
    val senderId: Long,
    val status: MeetingInviteStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)