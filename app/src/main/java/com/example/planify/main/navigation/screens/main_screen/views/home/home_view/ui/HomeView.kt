package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.planify.main.features.meeting.domain.services.MeetingService
import com.example.planify.main.navigation.screens.main_screen.Screen
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewModel
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewModelFactory
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewRoute
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.TopNavBar
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.ui_components.HomeDayView
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

    val uiState by viewModel.uiState.collectAsState()

    val currentWeekOffset by viewModel.currentWeekOffset.collectAsState()

    LaunchedEffect(currentWeekOffset) {
        val currentMonthTitle = viewModel.getMonthTitle(
            currentWeekOffset
        )
        setMonthTitle(currentMonthTitle)
    }

    val selectedDate by viewModel.selectedDate.collectAsState()

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
                onDateSelected = { viewModel.onDateSelected(it) }
            ) }
            screen(HomeViewRoute.Week) { HomeWeekView(
                selectedDate = selectedDate,
                uiState = uiState,
                onDateSelected = { viewModel.onDateSelected(it) },
                onWeekSynced = { viewModel.onWeekChanged(it) },
                getMeetingsInfo = { viewModel.getMeetingsInfo() },
                getMeetingsInfoByDate = { viewModel.getMeetingsInfoByDate(it) }
            ) }
            screen(HomeViewRoute.Month) { Screen() }
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