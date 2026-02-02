package com.example.planify.main.navigation.screens.main_screen.views.home.home_view

import android.provider.Contacts
import com.example.planify.main.features.meeting.entities.Invite
import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.features.profile.entities.Profile
import java.time.LocalDate

sealed interface UIState {
    object Loading: UIState

    data class ContentData(
        val meetingsInfo: Map<LocalDate, List<MeetingInfo>>
    ): UIState

    data class Error(val message: String): UIState
}
