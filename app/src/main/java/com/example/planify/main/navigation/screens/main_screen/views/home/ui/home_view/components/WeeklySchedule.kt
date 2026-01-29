package com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.entities.getWeekDays
import com.example.planify.main.common.themes.Locals
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun WeeklySchedule(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onWeekSynced: (Int) -> Unit
) {
    val initialPage = 500
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { 1000 }
    )

    LaunchedEffect(pagerState.currentPage) {
        onWeekSynced(pagerState.currentPage - initialPage)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
            ) { page ->
                val weekDays = remember(page) { getWeekDays(page - initialPage) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    weekDays.forEach { day ->
                        DayCard(
                            modifier = Modifier
                                .weight(1f)
                                .height(Locals.dimens.dayCardHeight),
                            day = day,
                            isSelected = day.date == selectedDate,
                            onClick = { onDateSelected(day.date) }
                        )
                    }
                }
            }
        }
    }
}
