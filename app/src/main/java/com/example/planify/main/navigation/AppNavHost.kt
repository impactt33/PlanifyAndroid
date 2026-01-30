package com.example.planify.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planify.main.features.auth.domain.services_impl.AuthServiceImplST
import com.example.planify.main.navigation.screens.init_screen.ui.InitScreen
import com.example.planify.main.navigation.screens.login_screen.LoginScreen
import com.example.planify.main.navigation.screens.main_screen.MainScreen

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
        composable(AppRoute.Main.route) { MainScreen() }
        composable(AppRoute.Login.route) { LoginScreen() }
    }
}