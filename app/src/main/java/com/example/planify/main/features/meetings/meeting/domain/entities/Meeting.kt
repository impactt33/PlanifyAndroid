package com.example.planify.main.features.meetings.meeting.domain.entities

import java.time.LocalDateTime
import java.time.Duration

data class Meeting(
    val id: Long,
    val ownerId: Long,
    val title: String,
    val description: String,
    val timeStart: LocalDateTime,
    val duration: Duration,
    val location: String
)