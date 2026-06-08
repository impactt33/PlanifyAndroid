package com.example.planify.main.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.planify.core.ui.dialogs.AlertDialog
import com.example.planify.main.common.notifications.NotificationDeepLinks
import com.example.planify.main.navigation.components.AuthRequiredDialog
import com.example.planify.main.navigation.screens.auth_screen.AuthScreen
import com.example.planify.main.navigation.screens.change_password_screens.ChangePasswordEmailConfirmScreen
import com.example.planify.main.navigation.screens.change_password_screens.ChangePasswordScreen
import com.example.planify.main.navigation.screens.change_password_screens.EnterEmailScreen
import com.example.planify.main.navigation.screens.create_meeting_screen.ui.CreateMeeting
import com.example.planify.main.navigation.screens.init_screen.ui.InitScreen
import com.example.planify.main.navigation.screens.main_screen.MainScreenBox
import com.example.planify.main.navigation.screens.main_screen.views.profile.ui.edit_profile.EditProfileScreen
import com.example.planify.main.navigation.screens.meeting_info_screen.MeetingInfoScreen
import com.example.planify.main.navigation.screens.notifications_screen.NotificationsScreen
import com.example.planify.main.navigation.screens.registration.RegistrationEmailConfirmScreen
import com.example.planify.main.navigation.screens.registration.RegistrationScreen
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
    val context = LocalContext.current

    var dialog by remember { mutableStateOf<DialogType?>(null) }

    var pendingMeetingId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        pendingMeetingId = extractMeetingId(context.findActivity()?.intent)
    }

    DisposableEffect(navController) {
        val activity = context.findActivity() as? ComponentActivity
        val listener = Consumer<Intent> { intent ->
            extractMeetingId(intent)?.let { meetingId ->
                navController.navigate(AppRoute.MeetingInfoMenu(meetingId).route) {
                    launchSingleTop = true
                }
            }
        }
        activity?.addOnNewIntentListener(listener)
        onDispose { activity?.removeOnNewIntentListener(listener) }
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val meetingId = pendingMeetingId ?: return@OnDestinationChangedListener
            if (destination.route == AppRoute.Main.route) {
                pendingMeetingId = null
                navController.navigate(AppRoute.MeetingInfoMenu(meetingId).route) {
                    launchSingleTop = true
                }
            }
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is NavHostUIEffect.Navigate -> {
                    Log.d("NavHostUIEffect", "Navigate: ${effect.route.route}")

                    val target = effect.route.route

                    navController.navigate(target) {
                        launchSingleTop = true
                        popUpTo(AppRoute.Init.route) { inclusive = true }
                    }
                }
                is NavHostUIEffect.ShowDialog ->{
                    dialog = effect.dialog
                    Log.d("NavHostUIEffect", "Dialog: ${effect.dialog}")
                }
            }
        }
    }

    dialog?.let { dialogInfo ->
        when (dialogInfo) {
            is DialogType.AuthError -> AuthRequiredDialog {
                navController.navigate(AppRoute.Auth.route) {
                    popUpTo(AppRoute.Main.route) { inclusive = true }
                    launchSingleTop = true
                }
                dialog = null }
            is DialogType.Generic -> AlertDialog(
                title = dialogInfo.title,
                message = dialogInfo.message,
                onCancel = { dialog = null },
                onDismiss = { dialog = null }
            )
        }
    }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppRoute.Init.route // <-----
    ) {
        composable(AppRoute.Init.route) {
            InitScreen(
                navHostController = navController
            )
        }
        composable(AppRoute.Main.route) {
            MainScreenBox(
                onSettings = { navController.navigate(AppRoute.Settings.route) },
                onCreateClick = { navController.navigate(AppRoute.CreateMeetingMenu.route) },
                onEditProfileClick = { navController.navigate(AppRoute.EditProfile.route) },
                navController = navController,
                onNotifications = { navController.navigate(AppRoute.Notifications.route) }
            )
        }
        composable(AppRoute.Auth.route) {
            AuthScreen(
                onRegister = { navController.navigate(AppRoute.Registration.route) },
                onForgetPassword = { navController.navigate(AppRoute.EnterEmail.route) },
                navHostController = navController
            )
        }
        composable(AppRoute.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.CreateMeetingMenu.route) {
            CreateMeeting(
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(
            route = AppRoute.MeetingInfoMenu.PATTERN,
            arguments = listOf(navArgument(AppRoute.MeetingInfoMenu.ARG) {type = NavType.LongType} )
        ) {
            MeetingInfoScreen(
                onBack = {
                    navController.popBackStack(
                        AppRoute.Main.route,
                        inclusive = false
                    )
                }
            )
        }
        composable(AppRoute.EditProfile.route) {
            EditProfileScreen(
                onBack = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                onCameraClick = { }
            )
        }

        composable(AppRoute.Notifications.route) {
            NotificationsScreen(
                navController = navController
            )
        }

        composable(AppRoute.Registration.route) {
            RegistrationScreen(
                navHostController = navController
            )
        }

        composable(
            route = AppRoute.ChangePasswordEmailConfirm.PATTERN,
            arguments = listOf(navArgument(AppRoute.ChangePasswordEmailConfirm.ARG) { type = NavType.StringType } )
        ) { backStackEntry ->
            ChangePasswordEmailConfirmScreen(
                challengeUUID = backStackEntry.arguments?.getString(AppRoute.ChangePasswordEmailConfirm.ARG),
                navController = navController
            )
        }

        composable(
            route = AppRoute.ChangePassword.PATTERN,
            arguments = listOf(navArgument(AppRoute.ChangePassword.ARG) { type = NavType.StringType } )
        ) { backStackEntry ->
            ChangePasswordScreen(
                challengeUUID = backStackEntry.arguments?.getString(AppRoute.ChangePassword.ARG),
                navController = navController
            )
        }

        composable(
            route = AppRoute.RegistrationEmailConfirm.PATTERN,
            arguments = listOf(navArgument(AppRoute.RegistrationEmailConfirm.ARG) { type = NavType.StringType } )
        ) { backStackEntry ->
            RegistrationEmailConfirmScreen(
                verificationUserId = backStackEntry.arguments?.getString(AppRoute.RegistrationEmailConfirm.ARG),
                navController = navController
            )
        }

        composable(AppRoute.EnterEmail.route) {
            EnterEmailScreen(
                navController = navController
            )
        }
    }
}

private fun extractMeetingId(intent: Intent?): Long? {
    val data: Uri = intent?.data ?: return null
    if (data.scheme != NotificationDeepLinks.SCHEME || data.host != NotificationDeepLinks.HOST) return null

    val segments = data.pathSegments
    return if (segments.size == 2 && segments[0] == "meeting") {
        segments[1].toLongOrNull()
    } else {
        null
    }
}

private fun Context.findActivity(): Activity? {
    var ctx: Context = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}