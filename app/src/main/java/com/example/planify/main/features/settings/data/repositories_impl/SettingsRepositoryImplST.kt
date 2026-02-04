package com.example.planify.main.features.settings.data.repositories_impl

import android.content.Context
import android.content.SharedPreferences
import com.example.planify.main.common.entities.ThemeId
import com.example.planify.main.features.settings.domain.repositories.SettingsRepository
import com.example.planify.main.features.settings.entities.LocalSettings
import androidx.core.content.edit
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.planify.core.SingletonHolder
import com.example.planify.main.features.settings.data.preferences.SettingsPreferences
import com.example.planify.main.features.settings.data.preferences.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepositoryImplST(
    context: Context
) : SettingsRepository {
    private val dataStore = context.settingsDataStore

    private fun fromPrimitives(
        theme: String?,
        notifications: Boolean?
    ): LocalSettings {
        val themeId = ThemeId.entries.firstOrNull { it.name == theme } ?: ThemeId.SYSTEM

        return LocalSettings(
            theme = themeId,
            notifications = notifications
        )
    }

    override val settingsFlow: Flow<LocalSettings> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            fromPrimitives(
                theme = preferences[SettingsPreferences.THEME],
                notifications = preferences[SettingsPreferences.NOTIFICATIONS]
            )
        }

    override suspend fun setTheme(theme: ThemeId): Result<Unit>  = runCatching{
        dataStore.edit { it[SettingsPreferences.THEME] = theme.name }
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean): Result<Unit> = runCatching {
        dataStore.edit { it[SettingsPreferences.NOTIFICATIONS] = enabled }
    }

    companion object : SingletonHolder<SettingsRepositoryImplST, Context>(::SettingsRepositoryImplST)
}
