package com.example.planify

import android.app.Application
import com.example.planify.main.features.auth.data.repositories_impl.AuthRepositoryImplST
import com.example.planify.main.features.auth.data.repositories_impl.UsersRepositoryImplST
import com.example.planify.main.features.auth.domain.services_impl.UsersServiceImplST
import com.example.planify.main.features.meetings.meeting.data.repositories_impl.MeetingRepositoryImplST
import com.example.planify.main.features.meetings.meeting.domain.services_impl.MeetingServiceImplST
import com.example.planify.main.features.profile.data.repositories_impl.ProfilesRepositoryImplST
import com.example.planify.main.features.profile.domain.services_impl.ProfilesServiceImplST
import com.example.planify.main.features.settings.data.repositories_impl.SettingsRepositoryImplST
import com.example.planify.main.features.settings.domain.services_impl.SettingsServiceImplST
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MeetingServiceImplST.init(MeetingRepositoryImplST)

        ProfilesServiceImplST.init(ProfilesRepositoryImplST)

        UsersServiceImplST.init(UsersRepositoryImplST)

        SettingsRepositoryImplST.init(this)

        SettingsServiceImplST.init(SettingsRepositoryImplST.get())
    }
}

// UI -> ViewModel -> Service (UseCases) -> Repository -> DataSource 💀💀💀
