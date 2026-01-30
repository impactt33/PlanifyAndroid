package com.example.planify.main.navigation.screens.main_screen.views.home.home_view

import com.example.planify.main.features.meeting.entities.Invite
import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.features.profile.Profile

sealed interface UIState {
    object Loading: UIState

    data class ContentData(
        val meetingsInfo: List<MeetingInfo>
    ): UIState
}
