package com.example.planify.main.navigation.screens.create_meeting_screen

import com.example.planify.main.features.meetings.domain.entities.MeetingContext

sealed interface UIState {
    object Creating: UIState

    data class ContentData (
        val meetingInfo: MeetingContext
    ) : UIState

    data class Error(val message: String): UIState
}