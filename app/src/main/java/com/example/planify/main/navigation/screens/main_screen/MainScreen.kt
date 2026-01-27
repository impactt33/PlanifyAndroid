package com.example.planify.main.navigation.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.features.home.ui.home_view.HomeView
import com.example.planify.main.navigation.screens.main_screen.components.BottomNavBar

@Composable
fun MainScreen() {
    val router = rememberPagerRouterScreenState(
        routes = MainScreenRoute.routes,
        startRoute = MainScreenRoute.Home
    )
    val colors = MaterialTheme.colorScheme

    Scaffold(
        bottomBar = { BottomNavBar(pagerRouter = router) },
        containerColor = colors.background
    ) { padding ->
        PagerRouterScreen(
            modifier = Modifier.fillMaxSize(),
            state = router
        ) {
            screen(MainScreenRoute.Home) {
                HomeView(scaffoldPadding = padding)
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