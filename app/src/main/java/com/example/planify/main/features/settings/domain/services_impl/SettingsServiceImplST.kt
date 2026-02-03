package com.example.planify.main.features.settings.domain.services_impl

import com.example.planify.main.features.settings.domain.repositories.SettingsRepository
import com.example.planify.main.features.settings.domain.services.SettingsService
import com.example.planify.main.features.settings.entities.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsServiceImplST(
    val settingsRepository: SettingsRepository
) : SettingsService {
    override fun getLocalSettings(): Result<List<Settings>> {
        return settingsRepository.getLocalSettings()
    }

}