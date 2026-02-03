package com.example.planify.main.navigation.screens.settings_screen

import com.example.planify.main.features.profile.entities.Profile
import com.example.planify.main.features.settings.entities.Settings

sealed interface UIState {
    object Loading: UIState

    data class ContentData(
        val settings: List<Settings>
    ): UIState

    data class Error(val message: String): UIState
}
