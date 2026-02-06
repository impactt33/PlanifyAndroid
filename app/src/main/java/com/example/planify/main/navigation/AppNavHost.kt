package com.example.planify.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planify.main.features.meetings.ui_screens.MeetingInfoScreen
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImpl
import com.example.planify.main.navigation.screens.auth_screen.AuthScreen
import com.example.planify.main.navigation.screens.create_meeting_screen.ui.CreateMeeting
import com.example.planify.main.navigation.screens.init_screen.ui.InitScreen
import com.example.planify.main.navigation.screens.main_screen.MainScreenBox
import com.example.planify.main.navigation.screens.main_screen.views.profile.ui.edit_profile.EditProfile
import com.example.planify.main.navigation.screens.settings_screen.SettingsViewModel
import com.example.planify.main.navigation.screens.settings_screen.ui.SettingsScreen

@Composable
fun AppNavHost() {
    AppNavHost(
        viewModel = hiltViewModel()
    )
}

@Composable
private fun AppNavHost(
    viewModel: AppNavHostViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { route ->
            navController.navigate(route.route)
        }
    }

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
        composable(AppRoute.Main.route) { MainScreenBox(
            onSettings = { navController.navigate(AppRoute.Settings.route) },
            onCreateClick = { navController.navigate(AppRoute.CreateMeetingMenu.route) },
            onEditProfileClick = { navController.navigate(AppRoute.EditProfile.route) }
        ) }
        composable(AppRoute.Auth.route) { AuthScreen(
            onRegister = { },
            onForgetPassword = { },
            navHostController = navController
        ) }
        composable(AppRoute.Settings.route) { SettingsScreen(
            onBack = { navController.popBackStack() }
        ) }
        composable(AppRoute.CreateMeetingMenu.route) {
            CreateMeeting(
                onBack = { navController.popBackStack() },
                onCreate = { navController.navigate(AppRoute.MeetingInfoMenu.route) }
            )
        }
        composable(AppRoute.MeetingInfoMenu.route) {
            MeetingInfoScreen(
                onBack = { navController.popBackStack(
                    AppRoute.Main.route,
                    inclusive = false
                ) }
            )
        }
        composable(AppRoute.EditProfile.route) {
            EditProfile(
                onBack = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                onCameraClick = { }
            )
        }
    }
}