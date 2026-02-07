package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.week_view

import java.time.LocalDate


sealed interface WeekUIState {
    data object Loading: WeekUIState

    data class ContentData(
        val meetingsInfoShort: Map<LocalDate, Int>
    ) : WeekUIState

    data class Error(val message: String): WeekUIState
}