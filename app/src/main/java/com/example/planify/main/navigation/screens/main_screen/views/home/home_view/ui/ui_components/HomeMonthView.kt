package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.TextEmptyMeetings
import com.example.planify.main.common.ui.objectClickable
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.common.utils.dateForPage
import com.example.planify.main.common.utils.monthForPage
import com.example.planify.main.common.utils.pageForDate
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.UIState
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.MeetingCard
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.CalendarDay
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.MonthScroll
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.ScrollableDateRow
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.SkeletonMeetingCard
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.getMonthDays
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.collections.emptyList

@Composable
fun HomeMonthView(
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate,
    pagerState: PagerState,
    initialPage: Int,
    getMeetingsInfoByDate: (LocalDate) -> List<MeetingInfo>,
    uiState: UIState,
    scaffoldPadding: PaddingValues
) {
    val scope = rememberCoroutineScope()

    @Suppress("DEPRECATION")
    val textFormat = pagerState.currentPage.monthForPage(initialPage)
        .format(ofPattern("LLLL yyyy", Locale("ru")))

    val colors = MaterialTheme.colorScheme

    val scrollState = rememberScrollState()

    val meetings = remember(selectedDate, uiState) {
        if (uiState is UIState.ContentData) getMeetingsInfoByDate(selectedDate)
        else emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            )
            .padding(
                bottom = scaffoldPadding.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ScrollableDateRow(
            pagerScope = scope,
            scrollPagerState = pagerState,
            textFormat = textFormat
        )
        WeekDaysRow()
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.xs),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MonthScroll(
                    modifier = Modifier
                        .wrapContentHeight()
                        .animateContentSize(),
                    selectedDate = selectedDate,
                    initialPage = initialPage,
                    pagerState = pagerState
                ) { page ->
                    val monthDays = remember(page) {
                        getMonthDays(page - initialPage, fixedWeeks = 6)
                    }

                    MonthCalendarGrid(
                        days = monthDays,
                        selectedDate = selectedDate,
                        onDateSelected = onDateSelected
                    )
                }
                Spacer(modifier = Modifier.height(Locals.spacing.m))
                Box(
                    modifier = Modifier
                        .withShapeBackground(
                            color = Locals.extras.border,
                            shape = Locals.shapes.smallShape
                        )
                        .fillMaxWidth()
                        .height(1.dp)
                )
                repeat(meetings.size) { row ->
                    MeetingCard(meetingInfo = meetings[row])
                }
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
fun WeekDaysRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.calendarCellSize)
    ) {
        repeat(7) { offset ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = DayOfWeek.MONDAY.plus(offset.toLong())
                        .getDisplayName(TextStyle.SHORT, Locale("ru"))
                        .replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = Locals.extras.mutedForeground.copy(
                        alpha = 0.7f
                    )
                )
            }
        }
    }
}

@Composable
fun CalendarCell(
    modifier: Modifier = Modifier,
    date: CalendarDay,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val textColor = when {
        isSelected -> colors.onPrimary
        date.isCurrentMonth -> colors.onSurface
        else -> colors.onSurfaceVariant.copy(alpha = 0.4f)
    }

    Box(
        modifier = modifier
            .withShapeBackground(
                shape = Locals.shapes.mediumShape,
                color = if (isSelected) colors.primary
                    else Color.Transparent
            )
            .objectClickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
fun MonthCalendarGrid(
    days: List<CalendarDay>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val cellSize = Locals.dimens.calendarCellSize

    val safeDays = remember(days) {
        if (days.size >= 42) days.take(42) else days
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(6) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                repeat(7) { col ->
                    val index = row * 7 + col
                    val day = safeDays.getOrNull(index)

                    if (day != null) {
                        CalendarCell(
                            date = day,
                            isSelected = day.date == selectedDate,
                            modifier = Modifier
                                .weight(1f)
                                .height(cellSize),
                            onClick = { onDateSelected(day.date) }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(cellSize)
                        )
                    }
                }
            }
        }
    }
}