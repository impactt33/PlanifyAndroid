package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.getWeekDays
import com.example.planify.main.common.themes.Locals
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate


@Composable
fun WeeklySchedule(
    selectedDate: LocalDate,
    pagerState: PagerState,
    initialPage: Int,
    setMonthTitle: (Int) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                setMonthTitle(page - initialPage)
            }
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
                beyondViewportPageCount = 1
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
