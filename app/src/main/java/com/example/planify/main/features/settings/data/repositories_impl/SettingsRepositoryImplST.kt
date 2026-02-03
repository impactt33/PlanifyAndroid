package com.example.planify.main.features.settings.data.repositories_impl

import com.example.planify.main.features.settings.domain.repositories.SettingsRepository
import com.example.planify.main.features.settings.entities.Settings

object SettingsRepositoryImplST : SettingsRepository {
    override fun getLocalSettings(): Result<List<Settings>> {
        return
    }
}
