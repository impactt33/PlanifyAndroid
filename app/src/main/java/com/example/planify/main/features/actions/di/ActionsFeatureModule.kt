package com.example.planify.main.features.actions.di

import com.example.planify.main.common.db.AppDatabase
import com.example.planify.main.features.actions.data.dao.ActionModelDAO
import com.example.planify.main.features.actions.data.sources.ActionsLocalDataSource
import com.example.planify.main.features.actions.data.sources.ActionsRemoteDataSource
import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActionsFeatureModule {
    @Provides
    @Singleton
    fun provideActionDataParser(): ActionDataParser = ActionDataParser()

    @Provides
    @Singleton
    fun provideActionModelDAO(
        appDatabase: AppDatabase
    ): ActionModelDAO = appDatabase.actionModelDAO()

    @Provides
    @Singleton
    fun provideActionsRemoteDataSource(
        authenticatedApiClient: AuthenticatedApiClient
    ): ActionsRemoteDataSource = ActionsRemoteDataSource(authenticatedApiClient)

    @Provides
    @Singleton
    fun provideActionLocalDataSource(
        actionModelDAO: ActionModelDAO
    ): ActionsLocalDataSource = ActionsLocalDataSource(actionModelDAO)
}
