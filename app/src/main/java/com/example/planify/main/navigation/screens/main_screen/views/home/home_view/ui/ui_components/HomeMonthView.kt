package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.objectClickable
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.ScheduleScroll
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.CalendarDay
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.MonthScroll
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.ScrollableDateRow
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.getMonthDays
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeMonthView(
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate,
    pagerState: PagerState,
    initialPage: Int,

) {
    val scope = rememberCoroutineScope()

    @Suppress("DEPRECATION")
    val textFormat = selectedDate.format(ofPattern("MMMM yyyy", Locale("ru")))

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ScrollableDateRow(
            pagerScope = scope,
            scrollPagerState = pagerState,
            textFormat = textFormat
        )

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeekDaysRow()
                MonthScroll(
                    onDateSelected = { onDateSelected(it) },
                    selectedDate = selectedDate,
                    initialPage = initialPage,
                    pagerState = pagerState
                ) { page ->
                    val monthDays = remember(page) { getMonthDays(page - initialPage) }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7)
                    ) {
                        items(monthDays) { day ->
                            CalendarCell(
                                date = day,
                                onClick = { onDateSelected(day.date) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Locals.spacing.xxl))
                    Box(
                        modifier = Modifier
                            .withShapeBackground(
                                color = Locals.extras.border,
                                shape = Locals.shapes.smallShape
                            )
                            .fillMaxWidth()
                            .height(1.dp)
                    )
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
                    .fillMaxHeight()
                    .border(
                        color = Locals.extras.border,
                        width = 1.dp
                    ),
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
    date: CalendarDay,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .height(Locals.dimens.calendarCellSize)
            .withShapeBackground(
                shape = Locals.shapes.mediumShape,
                color = if (isSelected) colors.primary
                    else Color.Transparent
            )
            .border(
                shape = Locals.shapes.mediumShape,
                color = Locals.extras.border,
                width = 1.dp
            )
            .objectClickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) colors.onPrimary
                else Color.Black
        )
    }
}