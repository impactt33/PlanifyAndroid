package com.example.planify.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.example.planify.main.navigation.screens.auth_screen.AuthScreen
import com.example.planify.main.navigation.screens.init_screen.ui.InitScreen
import com.example.planify.main.navigation.screens.main_screen.MainScreen
import com.example.planify.main.navigation.screens.settings_screen.ui.SettingsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppRoute.Init.route
    ) {
        composable(AppRoute.Init.route) {
            InitScreen(
                navHostController = navController
            )
        }
        composable(AppRoute.Main.route) {
            MainScreen(
                onSettings = { navController.navigate(AppRoute.Settings.route) }
            )
        }
        composable(AppRoute.Settings.route) {
            SettingsScreen(
                settingsService = SettingsServiceImplST.get()
            )
        }
        composable(AppRoute.Auth.route) { AuthScreen() }
    }
}
