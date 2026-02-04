package com.example.planify.main.features.profile.di

import com.example.planify.main.features.profile.data.repositories_impl.ProfilesRepositoryImpl
import com.example.planify.main.features.profile.domain.repositories.ProfilesRepository
import com.example.planify.main.features.profile.domain.services.ProfilesService
import com.example.planify.main.features.profile.domain.services_impl.ProfilesServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfilesFeatureBindingModule {
    @Binds
    @Singleton
    abstract fun bindProfilesRepository(impl: ProfilesRepositoryImpl) : ProfilesRepository

    @Binds
    @Singleton
    abstract fun bindProfilesService(impl: ProfilesServiceImpl) : ProfilesService
}