package com.example.planify.main.features.home.ui.home_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.features.home.ui.home_view.components.TopNavBar
import com.example.planify.main.features.home.ui.home_view.components.WeeklySchedule
import com.example.planify.main.navigation.screens.main_screen.Screen
import java.time.LocalDate


@Composable
fun HomeView(
    scaffoldPadding: PaddingValues
) {
    val router = rememberPagerRouterScreenState(
        routes = HomeViewRoute.routes,
        startRoute = HomeViewRoute.Week
    )

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
            screen(HomeViewRoute.Week) { HomeWeekView() }
            screen(HomeViewRoute.Month) { Screen() }
        }

    }

}

@Composable
fun HomeWeekView() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        WeeklySchedule(
            selectedDate,
            onDateSelected = { selectedDate = it }
        )
    }
}

// далее делать viewmodel