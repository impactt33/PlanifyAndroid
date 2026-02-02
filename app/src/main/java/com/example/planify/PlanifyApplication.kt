package com.example.planify

import android.app.Application
import com.example.planify.main.features.auth.data.repositories_impl.AuthRepositoryImplST
import com.example.planify.main.features.auth.data.repositories_impl.UsersRepositoryImplST
import com.example.planify.main.features.auth.domain.services_impl.AuthServiceImplST
import com.example.planify.main.features.auth.domain.services_impl.UsersServiceImplST
import com.example.planify.main.features.meeting.data.repositories_impl.MeetingRepositoryImplST
import com.example.planify.main.features.meeting.domain.services_impl.MeetingServiceImplST
import com.example.planify.main.features.profile.data.repositories_impl.ProfilesRepositoryImplST
import com.example.planify.main.features.profile.domain.services_impl.ProfilesServiceImplST

class PlanifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AuthServiceImplST.init(AuthRepositoryImplST)

        MeetingServiceImplST.init(MeetingRepositoryImplST)

        ProfilesServiceImplST.init(ProfilesRepositoryImplST)

        UsersServiceImplST.init(UsersRepositoryImplST)
    }
}

// UI -> ViewModel -> Service (Usecases) -> Repository -> DataSource 💀💀💀