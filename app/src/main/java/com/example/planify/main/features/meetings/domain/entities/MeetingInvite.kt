package com.example.planify.main.features.meetings.domain.entities

import com.example.planify.core.data.serializers.InstantToLocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MeetingInvite(
    val uuid: String,
    val meetingId: Long,
    val senderId: Long,
    val targetId: Long,
    val status: MeetingInviteStatus,
    @Serializable(InstantToLocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(InstantToLocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)
