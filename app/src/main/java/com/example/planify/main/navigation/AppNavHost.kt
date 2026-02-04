package com.example.planify.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planify.main.features.auth.domain.services_impl.AuthServiceImplST
import com.example.planify.main.features.settings.domain.services.SettingsService
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.example.planify.main.navigation.screens.init_screen.ui.InitScreen
import com.example.planify.main.navigation.screens.login_screen.LoginScreen
import com.example.planify.main.navigation.screens.main_screen.MainScreen
import com.example.planify.main.navigation.screens.settings_screen.ui.SettingsScreen

object TempGetAccessToken {
    val accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwianRpIjoiODMxZDA0NTctNjBiZi00YTMyLWJhYTgtZTU2OWVkNTJlMjIyIiwiaWF0IjoxNzcwMTYxMDAyLCJleHAiOjE3NzAxNjQ2MDIsInR5cGUiOjEsInVzZXJJZCI6MSwic2Vzc2lvblV1aWQiOiJmYmU2NzA5Ni05YzViLTQwMzktYjhmNi01ODY4ZWU3YzYzMzIifQ.8HCUQV0DU1PcsRPh6KNvz-l-O3xwUVYN7YvKKELTs-R7akj5jQlaAGhBG28WM-wy"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppRoute.Init.route
    ) {
        composable(AppRoute.Init.route) { InitScreen(
            navHostController = navController,
            authService = AuthServiceImplST.get()
            ) }
        composable(AppRoute.Main.route) { MainScreen(
            onSettings = { navController.navigate(AppRoute.Settings.route) }
        ) }
        composable(AppRoute.Login.route) { LoginScreen() }
        composable(AppRoute.Settings.route) { SettingsScreen(
            settingsService = SettingsServiceImplST.get()
        ) }
    }
}