package com.example.planify.main.navigation.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.features.meeting.domain.services.MeetingService
import com.example.planify.main.features.meeting.domain.services_impl.MeetingServiceImplST
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.HomeView
import com.example.planify.main.navigation.screens.main_screen.components.BottomNavBar
import com.example.planify.main.navigation.screens.main_screen.components.TopBar

@Composable
fun MainScreen() {
    val router = rememberPagerRouterScreenState(
        routes = MainScreenRoute.routes,
        startRoute = MainScreenRoute.Home
    )
    val colors = MaterialTheme.colorScheme

    var monthTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(
            pagerRouter = router,
            monthTitle = monthTitle
            ) },
        bottomBar = { BottomNavBar(pagerRouter = router) },
        containerColor = colors.background
    ) { padding ->
        PagerRouterScreen(
            modifier = Modifier.fillMaxSize(),
            state = router
        ) {
            screen(MainScreenRoute.Home) {
                HomeView(
                    scaffoldPadding = padding,
                    setMonthTitle = { monthTitle = it },
                    meetingService = MeetingServiceImplST.get()
                )
            }
            screen(MainScreenRoute.Chat) {Screen()}
            screen(MainScreenRoute.Inbox) {Screen()}
            screen(MainScreenRoute.Profile) {Screen()}
        }
    }
}

@Composable
fun Screen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("123123123")
    }
}