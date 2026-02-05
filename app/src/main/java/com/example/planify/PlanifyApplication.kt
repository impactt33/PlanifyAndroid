package com.example.planify

import android.app.Application
import com.example.planify.main.features.settings.data.repositories_impl.SettingsRepositoryImplST
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImplST
import com.google.crypto.tink.config.TinkConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTink()

        SettingsRepositoryImplST.init(this)
        SettingsServiceImplST.init(SettingsRepositoryImplST.get())
    }

    private fun initTink() {
        TinkConfig.register()
    }
}

// UI -> ViewModel -> Service (UseCases) -> Repository -> DataSource 💀💀💀
