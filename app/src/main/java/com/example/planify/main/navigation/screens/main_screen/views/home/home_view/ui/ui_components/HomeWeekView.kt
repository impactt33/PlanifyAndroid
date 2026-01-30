package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.UIState
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.ScheduleScroll
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.MeetingCard
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.WeeklySchedule
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.SkeletonMeetingCard
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

@Composable
fun HomeWeekView(
    selectedDate: LocalDate,
    uiState: UIState,
    onDateSelected: (LocalDate) -> Unit,
    onWeekSynced: (Int) -> Unit,
    getMeetingsInfo: () -> Unit,
    getMeetingsInfoByDate: (LocalDate) -> List<MeetingInfo>
) {
    val colors = MaterialTheme.colorScheme

    val initialPageTop = 500
    val pagerStateTop = rememberPagerState(
        initialPage = initialPageTop,
        pageCount = { 1000 }
    )

    val initialPageBottom = 5000
    val pagerStateBottom = rememberPagerState(
        initialPage = initialPageBottom,
        pageCount = { 10000 }
    )

    LaunchedEffect(selectedDate) {
        snapshotFlow { selectedDate }
            .collect {

            }

    }

    LaunchedEffect(Unit) {
        getMeetingsInfo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WeeklySchedule(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            onWeekSynced = onWeekSynced,
            initialPage = initialPageTop,
            pagerState = pagerStateTop
        )

        fun dateForPage(page: Int): LocalDate = LocalDate.now().plusDays(
            (page - initialPageBottom).toLong()
        )
        fun pageForDate(date: LocalDate): Int =
            ChronoUnit.DAYS.between(LocalDate.now(), date)
                .toInt() + initialPageBottom

        ScheduleScroll(
            modifier = Modifier
                .weight(1f),
            onDateSelected = onDateSelected,
            selectedDate = selectedDate,
            pagerState = pagerStateBottom,
            dateForPage = { dateForPage(it) },
            pageForDate = { pageForDate(it) }
        ) { page ->
            val date = dateForPage(page)
            val meetings = getMeetingsInfoByDate(date)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = Locals.spacing.xs,
                    bottom = Locals.dimens.bottomBarHeight
                ),
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.xs)
            ) {
                when (uiState) {
                    is UIState.Loading -> {
                        items(3) { SkeletonMeetingCard() }
                    }
                    is UIState.Error -> {
                        item { Text("Runtime error") }
                    }
                    is UIState.ContentData -> {
                        if (meetings.isEmpty()) {
                            item { Text("No meetings today") }
                        } else {
                            items(meetings) { info ->
                                MeetingCard(meetingInfo = info)
                            }
                        }
                    }
                }
            }
        }
    }
}