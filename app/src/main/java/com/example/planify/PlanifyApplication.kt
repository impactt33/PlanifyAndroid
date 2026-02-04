package com.example.planify

import android.app.Application
import com.example.planify.main.features.auth.data.repositories_impl.UsersRepositoryImpl
import com.example.planify.main.features.auth.domain.services_impl.UsersServiceImpl
import com.example.planify.main.features.meetings.meeting.data.repositories_impl.MeetingRepositoryImplST
import com.example.planify.main.features.meetings.meeting.domain.services_impl.MeetingServiceImplST
import com.example.planify.main.features.settings.data.repositories_impl.SettingsRepositoryImplST
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImplST
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MeetingServiceImplST.init(MeetingRepositoryImplST)


        SettingsRepositoryImplST.init(this)

        SettingsServiceImplST.init(SettingsRepositoryImplST.get())
    }
}

// UI -> ViewModel -> Service (UseCases) -> Repository -> DataSource 💀💀💀
