package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.common.utils.dateForPage
import com.example.planify.main.features.meetings.meetings.domain.entities.MeetingInfo
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.UIState
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.MeetingCard
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.ScheduleScroll
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.ScrollableDateRow
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.SkeletonMeetingCard
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Composable
fun HomeDayView(
    selectedDate: LocalDate,
    scrollPagerState: PagerState,
    initialPage: Int,
    uiState: UIState,
    onDateSelected: (LocalDate) -> Unit,
    getMeetingsInfoByDate: (LocalDate) -> List<MeetingInfo>
) {
    @Suppress("DEPRECATION")
    val textFormat = selectedDate.format(ofPattern("EEEE, d MMMM", Locale("ru")))

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableDateRow(
            pagerScope = scope,
            scrollPagerState = scrollPagerState,
            textFormat = textFormat
        )

        ScheduleScroll(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            pagerState = scrollPagerState,
            initialPage = initialPage
        ) { page ->
            val pageDate = remember(page) { page.dateForPage(initialPage) }

            val timeSlots = remember(pageDate) {
                (7..23).map { hour ->
                    LocalDateTime.of(pageDate, LocalTime.of(hour, 0))
                }
            }

            val meetings = remember(pageDate, uiState) {
                if (uiState is UIState.ContentData) getMeetingsInfoByDate(pageDate)
                    else emptyList()
            }

            val meetingsByStart = remember(pageDate) {
                meetings.associateBy { it.meeting.timeStart }
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
                        items(
                            items = timeSlots,
                            key = { it }
                        ) { timeSlot ->
                            val meeting = meetingsByStart[timeSlot]

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(0.15f)
                                        .heightIn(min = Locals.dimens.emptyTimeSlot),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(Locals.spacing.xs),
                                            text = timeSlot.format(ofPattern("HH:mm")),
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Normal
                                            ),
                                            color = Locals.extras.mutedForeground
                                        )

                                        Box(
                                            modifier = Modifier
                                                .withShapeBackground(
                                                    shape = Locals.shapes.smallShape,
                                                    color = Locals.extras.mutedForeground.copy(
                                                        alpha = 0.2f
                                                    )
                                                )
                                                .height(
                                                    if (meeting == null) Locals.dimens.emptyTimeSlot
                                                    else Locals.dimens.meetingCardHeight
                                                )
                                                .width(2.dp)
                                                .align(Alignment.CenterEnd)
                                        )
                                    }
                                }
                                if (meeting != null) {
                                    MeetingCard(
                                        meetingInfo = meeting
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(Locals.spacing.m),
                                            text = stringResource(R.string.free),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Locals.extras.mutedForeground.copy(
                                                alpha = 0.4f
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}