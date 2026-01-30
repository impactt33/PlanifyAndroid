package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.UIState
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.DateScroll
import java.time.LocalDate

@Composable
fun HomeDayView(
    selectedDate: LocalDate,
    //uiState: UIState,
    onDateSelected: (LocalDate) -> Unit,
    //getMeetingsInfo: () -> Unit,
    //getMeetingsInfoByDate: (LocalDate) -> List<MeetingInfo>
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateScroll(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )
    }
}