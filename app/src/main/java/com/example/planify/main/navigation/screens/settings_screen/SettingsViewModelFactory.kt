package com.example.planify.main.navigation.screens.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planify.main.features.settings.domain.services.SettingsService

class SettingsViewModelFactory(
    val settingsService: SettingsService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                settingsService = settingsService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}