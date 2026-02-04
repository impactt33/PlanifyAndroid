package com.example.planify.main.navigation

sealed class AppRoute(val route: String) {
    object Main : AppRoute("main")
    object Auth : AppRoute("auth")
    object Init : AppRoute("init")
    object Settings : AppRoute("settings")
}
