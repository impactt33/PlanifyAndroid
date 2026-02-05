package com.example.planify.main.features.meetings.data.dto

import com.example.planify.main.features.meetings.domain.entities.Meeting
import java.time.LocalDateTime

data class MeetingDTO(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val description: String,
    val location: String,
    val startsAt: LocalDateTime,
    val duration: Int
) {
    fun toEntity(): Meeting = Meeting(
        id = id,
        ownerId = ownerId,
        name = name,
        description = description,
        location = location,
        startsAt = startsAt,
        duration = duration
    )
}
