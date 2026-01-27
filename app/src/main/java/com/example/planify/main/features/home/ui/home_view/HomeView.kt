package com.example.planify.main.features.home.ui.home_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.GreetingText
import com.example.planify.main.common.ui.UpcomingEventText
import com.example.planify.main.features.home.ui.home_view.components.UpcomingEventBanner
import com.example.planify.main.features.home.ui.home_view.components.WeeklySchedule
import java.time.LocalDate


private val events = hashMapOf<String, String>(
    "Meeting 1" to "08:00 - 10:00 Pm",
    "Meeting 1" to "08:00 - 10:00 Pm",
    "Meeting 1" to "08:00 - 10:00 Pm",
    "Meeting 1" to "08:00 - 10:00 Pm"
)
@Composable
fun HomeView(scaffoldPadding: PaddingValues) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GreetingText(
                modifier = Modifier
                    .padding(horizontal = Locals.spacing.m),
                name = stringResource(R.string.username)
            )
            UpcomingEventBanner(
                time = "08:00 - 10:00 Pm",
                event = "Meeting"
            )
            WeeklySchedule(
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                }
            )
        }
    }
}

// далее делать viewmodel