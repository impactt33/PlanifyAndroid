package com.example.planify.main.features.meetings.di

import com.example.planify.main.features.meetings.data.repositories_impl.MeetingsRepositoryImpl
import com.example.planify.main.features.meetings.domain.repositories.MeetingsRepository
import com.example.planify.main.features.meetings.domain.services.MeetingService
import com.example.planify.main.features.meetings.domain.services_impl.MeetingServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MeetingsFeatureBindingModule {
    @Binds
    @Singleton
    abstract fun bindMeetingsRepository(impl: MeetingsRepositoryImpl): MeetingsRepository

    @Binds
    @Singleton
    abstract fun bindMeetingsService(impl: MeetingServiceImpl): MeetingService
}
