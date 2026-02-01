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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.ScrollableDateRow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeMonthView(
    selectedDate: LocalDate,
    pagerState: PagerState
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7)
                ) {
                    items(30) {
                        CalendarCell(
                            date = LocalDate.now(),
                            isSelected = false
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
    date: LocalDate,
    isSelected: Boolean
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
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) colors.onPrimary
                else Color.Black
        )
    }
}