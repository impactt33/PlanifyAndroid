package com.example.planify.main.features.settings.domain.services

import com.example.planify.main.features.settings.entities.Settings

interface SettingsService {
    fun getLocalSettings(): Result<List<Settings>>
}