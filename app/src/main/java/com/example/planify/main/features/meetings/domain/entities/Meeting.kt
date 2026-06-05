package com.example.planify.main.features.meetings.domain.entities

import com.example.planify.core.data.serializers.InstantToLocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Meeting(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val description: String,
    val location: String,
    @Serializable(with = InstantToLocalDateTimeSerializer::class)
    val startsAt: LocalDateTime,
    val duration: Int
)
