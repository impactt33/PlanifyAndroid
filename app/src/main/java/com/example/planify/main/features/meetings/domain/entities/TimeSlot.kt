package com.example.planify.main.features.meetings.domain.entities

data class TimeSlot(
    val id: String,
    val label: String,
    val isBusy: Boolean
)
