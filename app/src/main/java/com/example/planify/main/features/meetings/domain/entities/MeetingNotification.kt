package com.example.planify.main.features.meetings.domain.entities

data class MeetingNotification (
    val id: Long,
    val senderName: String,
    val text: String
)