package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import java.time.LocalDate

@Composable
fun MonthScroll(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate,
    pagerState: PagerState,
    initialPage: Int,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        pageContent(page)
    }
}