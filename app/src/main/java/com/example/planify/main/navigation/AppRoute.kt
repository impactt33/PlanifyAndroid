package com.example.planify.main.navigation

sealed class AppRoute(val route: String) {
    object Main : AppRoute("main")
}