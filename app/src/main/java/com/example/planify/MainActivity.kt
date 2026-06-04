package com.example.planify

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planify.main.common.entities.ThemeId
import com.example.planify.main.common.themes.PlanifyTheme
import com.example.planify.main.features.settings.domain.entities.LocalSettings
import com.example.planify.main.navigation.AppNavHost
import com.example.planify.main.navigation.screens.settings_screen.SettingsViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                Log.d("FCM_TOKEN", "Token: $token")
            }

            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val settings by settingsViewModel.settingsService.settingsFlow.collectAsStateWithLifecycle(
                initialValue = LocalSettings(
                    theme = ThemeId.SYSTEM,
                    notifications = true
                )
            )

            val darkTheme = when(settings.theme) {
                ThemeId.DARK -> true
                ThemeId.LIGHT -> false
                ThemeId.SYSTEM -> isSystemInDarkTheme()
            }

            PlanifyTheme(darkTheme = darkTheme) {
                AppNavHost()
            }
        }
    }
}