package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate

@Composable
fun ScheduleScroll(
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    dateForPage: (Int) -> LocalDate,
    pageForDate: (LocalDate) -> Int,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    val latestSelectedDate = rememberUpdatedState(selectedDate)
    val latestOnDateSelected = rememberUpdatedState(onDateSelected)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                val newDate = dateForPage(page)
                if (newDate != latestSelectedDate.value) {
                    latestOnDateSelected.value(newDate)
                }
            }
    }

    LaunchedEffect(selectedDate) {
        val target = pageForDate(selectedDate)
        if (target != pagerState.currentPage) {
            pagerState.scrollToPage(target)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
        ) { page ->
        pageContent(page)
    }
}