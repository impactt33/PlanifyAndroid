package com.example.planify.main.features.firebase_fcm.di

import com.example.planify.main.features.firebase_fcm.data.data_source.RemoteFcmDataSource
import com.example.planify.main.features.firebase_fcm.data.data_source_impl.RemoteFcmDataSourceImpl
import com.example.planify.main.features.firebase_fcm.data.repositories_impl.FcmRepositoryImpl
import com.example.planify.main.features.firebase_fcm.domain.repositories.FcmRepository
import com.example.planify.main.features.firebase_fcm.domain.services.FcmService
import com.example.planify.main.features.firebase_fcm.domain.services_impl.FcmServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FcmFeatureModule {
    @Binds
    @Singleton
    abstract fun bindRemoteFcmDataSource(impl: RemoteFcmDataSourceImpl): RemoteFcmDataSource

    @Binds
    @Singleton
    abstract fun bindFcmRepository(impl: FcmRepositoryImpl): FcmRepository

    @Binds
    @Singleton
    abstract fun bindFcmService(impl: FcmServiceImpl): FcmService
}