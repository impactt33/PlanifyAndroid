package com.example.planify.main.features.settings.domain.services_impl

import com.example.planify.core.SingletonHolder
import com.example.planify.main.common.entities.ThemeId
import com.example.planify.main.features.settings.domain.repositories.SettingsRepository
import com.example.planify.main.features.settings.domain.services.SettingsService
import com.example.planify.main.features.settings.entities.LocalSettings
import kotlinx.coroutines.flow.Flow

class SettingsServiceImplST(
    val settingsRepository: SettingsRepository
) : SettingsService {

    override val settingsFlow: Flow<LocalSettings>
        get() = settingsRepository.settingsFlow

    override suspend fun setTheme(theme: ThemeId): Result<Unit> {
        return settingsRepository.setTheme(theme)
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean): Result<Unit> {
        return settingsRepository.setNotificationsEnabled(enabled)
    }

    companion object : SingletonHolder<SettingsServiceImplST, SettingsRepository>(::SettingsServiceImplST)

}