package com.example.planify.main.features.meeting.entities

import java.time.LocalDateTime
import java.time.Duration

data class Meeting(
    val title: String,
    val description: String,
    val timeStart: LocalDateTime,
    val duration: Duration,
    val location: String
)