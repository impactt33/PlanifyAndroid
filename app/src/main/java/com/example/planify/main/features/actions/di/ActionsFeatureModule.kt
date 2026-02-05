package com.example.planify.main.features.actions.di

import com.example.planify.main.features.actions.domain.utils.ActionDataParser
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
}
