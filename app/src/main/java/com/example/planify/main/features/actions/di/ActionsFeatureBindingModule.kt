package com.example.planify.main.features.actions.di

import com.example.planify.main.features.actions.data.repositories_impl.ActionsRepositoryImpl
import com.example.planify.main.features.actions.domain.repositories.ActionsRepository
import com.example.planify.main.features.actions.domain.services.ActionsService
import com.example.planify.main.features.actions.domain.services_impl.ActionsServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActionsFeatureBindingModule {
    @Binds
    @Singleton
    abstract fun bindActionsRepository(impl: ActionsRepositoryImpl): ActionsRepository

    @Binds
    @Singleton
    abstract fun bindActionsService(impl: ActionsServiceImpl): ActionsService
}
