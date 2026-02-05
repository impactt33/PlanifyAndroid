package com.example.planify

import android.app.Application
import com.example.planify.main.features.settings.data.repositories_impl.SettingsRepositoryImplST
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImplST
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SettingsRepositoryImplST.init(this)

        SettingsServiceImplST.init(SettingsRepositoryImplST.get())
    }
}

// UI -> ViewModel -> Service (UseCases) -> Repository -> DataSource 💀💀💀
