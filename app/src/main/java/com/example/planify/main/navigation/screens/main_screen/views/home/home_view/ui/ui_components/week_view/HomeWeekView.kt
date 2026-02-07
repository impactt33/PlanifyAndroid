package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.week_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.TextEmptyMeetings
import com.example.planify.main.common.utils.dateForPage
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.UIState
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.scrolls.ScheduleScroll
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.skeleton_meeting_card.MeetingCard
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.WeeklySchedule
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.skeleton_meeting_card.SkeletonMeetingCard
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.Locale


@Composable
fun HomeWeekView(
    selectedDate: LocalDate,
    uiState: UIState,
    scrollPagerState: PagerState,
    initialPageBottom: Int,
    onDateSelected: (LocalDate) -> Unit,
    setMonthTitle: (String) -> Unit,
    getMeetingsInfoByDate: (LocalDate) -> List<MeetingContext>,
    onMeetingClick: (Long) -> Unit
) {
    HomeWeekView(
        viewModel = hiltViewModel(),
        selectedDate = selectedDate,
        uiState = uiState,
        scrollPagerState = scrollPagerState,
        initialPageBottom = initialPageBottom,
        onDateSelected = onDateSelected,
        setMonthTitle = setMonthTitle,
        getMeetingsInfoByDate = getMeetingsInfoByDate,
        onMeetingClick = onMeetingClick
    )
}

@Composable
private fun HomeWeekView(
    viewModel: HomeWeekViewModel,
    selectedDate: LocalDate,
    uiState: UIState,
    scrollPagerState: PagerState,
    initialPageBottom: Int,
    onDateSelected: (LocalDate) -> Unit,
    setMonthTitle: (String) -> Unit,
    getMeetingsInfoByDate: (LocalDate) -> List<MeetingContext>,
    onMeetingClick: (Long) -> Unit
) {
    val weekUiState by viewModel.uiState.collectAsState()

    val colors = MaterialTheme.colorScheme

    val initialPageTop = 500
    val pagerStateTop = rememberPagerState(
        initialPage = initialPageTop,
        pageCount = { 1000 }
    )

    fun pageForWeek(date: LocalDate): Int =
        ChronoUnit.WEEKS.between(LocalDate.now().
            with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
            date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
            .toInt()

    LaunchedEffect(selectedDate) {
        snapshotFlow { selectedDate }
            .collect {
                val currentPage = pageForWeek(selectedDate)
                pagerStateTop.animateScrollToPage(initialPageTop + currentPage)
            }
    }

    @Suppress("DEPRECATION")
    fun getMonthTitle(offset: Int) = LocalDate.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .plusWeeks(offset.toLong())
            .format(ofPattern("LLLL yyyy", Locale("ru")))
            .replaceFirstChar { it.uppercase() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WeeklySchedule(
            weekUiState = weekUiState,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            initialPage = initialPageTop,
            pagerState = pagerStateTop,
            setMonthTitle = {
                setMonthTitle(
                    getMonthTitle(it)
                )
            }
        )

        ScheduleScroll(
            modifier = Modifier
                .weight(1f),
            onDateSelected = onDateSelected,
            selectedDate = selectedDate,
            pagerState = scrollPagerState,
            initialPage = initialPageBottom
        ) { page ->

            val meetings = remember(selectedDate, uiState) {
                if (uiState is UIState.ContentData) getMeetingsInfoByDate(page.dateForPage(initialPageBottom))
                    else emptyList()
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = Locals.spacing.xs,
                    bottom = Locals.dimens.bottomBarHeight
                ),
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.xs),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (uiState) {
                    is UIState.Loading -> {
                        items(3) { SkeletonMeetingCard() }
                    }
                    is UIState.Error -> {
                        item {
                            Text("Runtime error")
                        }
                    }
                    is UIState.ContentData -> {
                        if (meetings.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(0.8f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TextEmptyMeetings()
                                }
                            }
                        } else {
                            items(meetings) { info ->
                                MeetingCard(
                                    meetingInfo = info,
                                    onClick = onMeetingClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}