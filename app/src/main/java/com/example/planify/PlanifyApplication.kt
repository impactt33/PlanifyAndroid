package com.example.planify

import android.app.Application
import com.example.planify.main.features.auth.data.repositories_impl.AuthRepositoryImplST
import com.example.planify.main.features.auth.domain.repositories.AuthRepository
import com.example.planify.main.features.auth.domain.services_impl.AuthServiceImplST

class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AuthServiceImplST.init(AuthRepositoryImplST)
    }
}

// UI -> ViewModel -> Service (Usecases) -> Repository -> DataSource 💀💀💀