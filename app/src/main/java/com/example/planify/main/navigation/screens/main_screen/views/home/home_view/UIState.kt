package com.example.planify.main.navigation.screens.main_screen.views.home.home_view

import com.example.planify.main.features.meeting.domain.entities.MeetingInfo
import java.time.LocalDate

sealed interface UIState {
    data object Loading: UIState

    data class Error(val message: String): UIState

    data class ContentData(
        val meetingsInfo: Map<LocalDate, List<MeetingInfo>>
    ): UIState
}
