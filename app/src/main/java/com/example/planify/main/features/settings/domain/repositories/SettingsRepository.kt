package com.example.planify.main.features.settings.domain.repositories

import com.example.planify.main.features.settings.entities.Settings

interface SettingsRepository {
    fun getLocalSettings(): Result<List<Settings>>
}