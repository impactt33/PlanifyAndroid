package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planify.core.ui.pager_router_screen.PagerRouterNavigator
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.meeting.domain.services.MeetingService
import com.example.planify.main.features.meeting.entities.Invite
import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.features.profile.Profile
import com.example.planify.main.navigation.screens.init_screen.components.LoadingView
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.TopNavBar
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.WeeklySchedule
import com.example.planify.main.navigation.screens.main_screen.Screen
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewModel
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewModelFactory
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.HomeViewRoute
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.UIState
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.MeetingCard
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.SkeletonMeetingCard
import java.time.LocalDate

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
            screen(HomeViewRoute.Day) { Screen() }
            screen(HomeViewRoute.Week) { HomeWeekView(
                selectedDate = selectedDate,
                uiState = uiState,
                onDateSelected = {
                    viewModel.onDateSelected(it)

                },
                onWeekSynced = { viewModel.onWeekChanged(it) },
                getMeetingsInfo = { viewModel.getMeetingsInfo() }
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

@Composable
fun HomeWeekView(
    selectedDate: LocalDate,
    uiState: UIState,
    onDateSelected: (LocalDate) -> Unit,
    onWeekSynced: (Int) -> Unit,
    getMeetingsInfo: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(selectedDate) {
        getMeetingsInfo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WeeklySchedule(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            onWeekSynced = onWeekSynced
        )

        when (uiState) {
            is UIState.Loading -> {
                SkeletonMeetingCard()
            }
            is UIState.ContentData -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(
                        top = Locals.spacing.xs,
                        bottom = Locals.dimens.bottomBarHeight
                    ),
                    verticalArrangement = Arrangement.spacedBy(Locals.spacing.xs)
                ) {
                    items(uiState.meetingsInfo) { meetingInfo ->
                        MeetingCard(
                            meetingInfo = meetingInfo
                        )
                    }
                }
            }
        }

    }
}