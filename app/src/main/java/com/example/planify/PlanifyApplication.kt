package com.example.planify

import android.app.Application
import com.example.planify.main.features.auth.data.repositories_impl.AuthRepositoryImplST
import com.example.planify.main.features.auth.domain.repositories.AuthRepository
import com.example.planify.main.features.auth.domain.services_impl.AuthServiceImplST
import com.example.planify.main.features.meeting.data.repositories_impl.MeetingRepositoryImplST
import com.example.planify.main.features.meeting.domain.repositories.MeetingRepository
import com.example.planify.main.features.meeting.domain.services_impl.MeetingServiceImplST

class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AuthServiceImplST.init(AuthRepositoryImplST)

        MeetingServiceImplST.init(MeetingRepositoryImplST)
    }
}

// UI -> ViewModel -> Service (Usecases) -> Repository -> DataSource 💀💀💀