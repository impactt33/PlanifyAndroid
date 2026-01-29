package com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view.components.TopNavBar
import com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view.components.WeeklySchedule
import com.example.planify.main.navigation.screens.main_screen.Screen
import com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view.components.MeetingCard
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

    val currentWeekOffset by viewModel.currentWeekOffset.collectAsState()

    LaunchedEffect(currentWeekOffset) {
        val currentMonthTitle = viewModel.getMonthTitle(
            currentWeekOffset
        )
        setMonthTitle(currentMonthTitle)
    }

    val selectedDate = viewModel.selectedDate.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
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
                selectedDate = selectedDate.value,
                onDateSelected = { viewModel.onDateSelected(it) },
                onWeekSynced = { viewModel.onWeekChanged(it) }
            ) }
            screen(HomeViewRoute.Month) { Screen() }
        }
    }

}

@Composable
fun HomeView(
    scaffoldPadding: PaddingValues,
    setMonthTitle: (String) -> Unit
) {
    val factory = remember { HomeViewModelFactory() }

    HomeView(
        viewModel = viewModel(factory = factory),
        scaffoldPadding = scaffoldPadding,
        setMonthTitle = setMonthTitle
    )
}

@Composable
fun HomeWeekView(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onWeekSynced: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        WeeklySchedule(
            selectedDate,
            onDateSelected = onDateSelected,
            onWeekSynced = onWeekSynced
        )
        MeetingCard(
            title = "TestMeet",
            description = "TestMeetDesc",
            time = "12 - 13 pm",
            location = "A123",
            participants = "IGOR IGOR OLEG"
        )
    }
}