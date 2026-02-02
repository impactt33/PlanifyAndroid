package com.example.planify.main.navigation.screens.main_screen.views.profile

import com.example.planify.main.features.profile.entities.Profile

sealed interface UIState {
    object Loading: UIState

    data class ContentData(
        val profile: Profile
    ): UIState

    data class Error(val message: String): UIState
}
