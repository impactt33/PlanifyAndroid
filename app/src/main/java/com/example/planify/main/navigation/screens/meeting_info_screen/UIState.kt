package com.example.planify.main.navigation.screens.meeting_info_screen

import com.example.planify.main.features.meetings.domain.entities.MeetingContext

sealed class UIState {
    object Loading: UIState()

    data class ContentData(
        val meetingContext: MeetingContext
    ): UIState()

    data class Error(val message: String): UIState()
}