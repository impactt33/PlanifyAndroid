package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.common.utils.pageForDate
import com.example.planify.main.features.meetings.meetings.domain.services.MeetingService
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewModel
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewModelFactory
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewRoute
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.TopNavBar
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.HomeDayView
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.HomeMonthView
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.HomeWeekView

@Composable
private fun HomeView(
    viewModel: HomeViewModel,
    scaffoldPadding: PaddingValues,
    setMonthTitle: (String) -> Unit
) {
    val router = rememberPagerRouterScreenState(
        routes = HomeViewRoute.routes,
        startRoute = HomeViewRoute.Week
    )

//    val dayInitialPage = 5000
//    val dayPagerState = rememberPagerState(
//        initialPage = dayInitialPage,
//        pageCount = { 10000 }
//    )
//
//    val weekInitialPage = 500
//    val weekPagerState = rememberPagerState(
//        initialPage = weekInitialPage,
//        pageCount = { 1000 }
//    )
//
//    val monthInitialPage = 500
//    val monthPagerState = rememberPagerState(
//        initialPage = monthInitialPage,
//        pageCount = { 1000 }
//    )

    val initialScrollPage = 5000
    val scrollPagerState = rememberPagerState(
        initialPage = initialScrollPage,
        pageCount = { 10000 }
    )

    val initialCalendarPage = 500
    val calendarPagerState = rememberPagerState(
        initialPage = initialCalendarPage,
        pageCount = { 1000 }
    )

    val uiState by viewModel.uiState.collectAsState()

    val selectedDate by viewModel.selectedDate.collectAsState()

    // Sync calendarPager and ScrollPager(Day + BottomWeek scroll)

    LaunchedEffect(selectedDate) {
        val target = selectedDate.pageForDate(initialPage = initialScrollPage)

        if(!scrollPagerState.isScrollInProgress && scrollPagerState.currentPage != target) {
            scrollPagerState.scrollToPage(target)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getMeetingsInfo()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = scaffoldPadding.calculateTopPadding()
            ),
        topBar = { TopNavBar(pagerRouter = router) }
    ) { padding ->
        PagerRouterScreen(
            modifier = Modifier
                .padding(padding),
            userScrollEnabled = false,
            state = router
        ) {
            screen(HomeViewRoute.Day) { HomeDayView(
                selectedDate = selectedDate,
                onDateSelected = { viewModel.onDateSelected(it) },
                scrollPagerState = scrollPagerState,
                initialPage = initialScrollPage,
                uiState = uiState,
                getMeetingsInfoByDate = { viewModel.getMeetingsInfoByDate(it) }
            ) }
            screen(HomeViewRoute.Week) { HomeWeekView(
                selectedDate = selectedDate,
                uiState = uiState,
                scrollPagerState = scrollPagerState,
                initialPageBottom = initialScrollPage,
                onDateSelected = { viewModel.onDateSelected(it) },
                getMeetingsInfoByDate = { viewModel.getMeetingsInfoByDate(it) },
                setMonthTitle = { setMonthTitle(it) }
            ) }
            screen(HomeViewRoute.Month) {
                HomeMonthView(
                    selectedDate = selectedDate,
                    pagerState = calendarPagerState,
                    initialPage = initialCalendarPage,
                    onDateSelected = { viewModel.onDateSelected(it) },
                    getMeetingsInfoByDate = { viewModel.getMeetingsInfoByDate(it) },
                    uiState = uiState,
                    scaffoldPadding = scaffoldPadding
                )
            }
        }
    }

}

@Composable
fun HomeView(
    scaffoldPadding: PaddingValues,
    setMonthTitle: (String) -> Unit,
    meetingService: MeetingService
) {
    val factory = remember { HomeViewModelFactory(meetingService = meetingService) }

    HomeView(
        viewModel = viewModel(factory = factory),
        scaffoldPadding = scaffoldPadding,
        setMonthTitle = setMonthTitle
    )
}