package com.example.planify.main.navigation.screens.create_meeting_screen

import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.profiles.domain.entities.Profile


data class ProfileSearchState(
    val query: String = "",
    val items: List<Profile> = emptyList(),
    val page: Int = 0,
    val last: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface UIState {
    object Creating: UIState

    data class ContentData (
        val meetingInfo: MeetingContext,
        val profiles: ProfileSearchState = ProfileSearchState(),
        val selectedIds: Set<Long> = emptySet()
    ) : UIState

    data class Error(val message: String): UIState
}