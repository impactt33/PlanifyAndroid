package com.example.planify

import android.app.Application
import com.google.crypto.tink.config.TinkConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTink()
    }

    private fun initTink() {
        TinkConfig.register()
    }
}

// UI -> ViewModel -> Service (UseCases) -> Repository -> DataSource 💀💀💀
