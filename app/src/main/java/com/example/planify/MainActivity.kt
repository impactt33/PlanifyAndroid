package com.example.planify

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planify.main.common.entities.ThemeId
import com.example.planify.main.common.themes.PlanifyTheme
import com.example.planify.main.features.settings.domain.entities.LocalSettings
import com.example.planify.main.navigation.AppNavHost
import com.example.planify.main.navigation.screens.settings_screen.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RequestNotificationPermission()

            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val settings by settingsViewModel.settingsService.settingsFlow.collectAsStateWithLifecycle(
                initialValue = LocalSettings(
                    theme = ThemeId.SYSTEM,
                    notifications = true
                )
            )

            val darkTheme = when (settings.theme) {
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

@Composable
private fun RequestNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {  }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}