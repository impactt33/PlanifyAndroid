package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun MonthScroll(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    pagerState: PagerState,
    initialPage: Int,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect {
            }
    }

    LaunchedEffect(selectedDate) {
        snapshotFlow { selectedDate }
            .collect {
                val target = initialPage + ChronoUnit.MONTHS
                    .between(LocalDate.now().withDayOfMonth(1),
                        selectedDate.withDayOfMonth(1))
                    .toInt()

                if (pagerState.currentPage != target) {
                    pagerState.animateScrollToPage(target)
                }
            }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        beyondViewportPageCount = 1
    ) { page ->
        pageContent(page)
    }
}