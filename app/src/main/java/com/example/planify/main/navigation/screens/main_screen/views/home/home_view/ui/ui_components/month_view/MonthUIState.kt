package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.month_view

import java.time.LocalDate

sealed interface MonthUIState {
    data object Loading: MonthUIState

    data class ContentData(
        val meetingsInfoShort: Map<LocalDate, Int>
    ) : MonthUIState

    data class Error(val message: String): MonthUIState
}