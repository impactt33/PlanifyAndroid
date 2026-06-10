package com.example.planify.main.navigation.screens.main_screen

import com.example.planify.core.ui.pager_router_screen.PagerRoute

sealed class MainScreenRoute(override val key: String) : PagerRoute {
    object Home : MainScreenRoute("home")
    object Favorites : MainScreenRoute("favorites")
    object Inbox : MainScreenRoute("inbox")
    object Profile : MainScreenRoute("profile")

    companion object {
        val routes: List<MainScreenRoute> = listOf(Home, Favorites, Inbox, Profile)
    }
}
