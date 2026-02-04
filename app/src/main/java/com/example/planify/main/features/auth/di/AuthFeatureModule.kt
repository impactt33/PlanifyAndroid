package com.example.planify.main.features.auth.di

import com.example.planify.main.common.network.policies.app_code.AppCodeProcessingPolicy
import com.example.planify.main.features.auth.domain.AuthTokenManager
import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthFeatureModule {
    @Provides
    @Singleton
    fun provideAuthenticatedApiClient(
        tokenManager: AuthTokenManager,
        httpClient: HttpClient,
        policy: AppCodeProcessingPolicy
    ): AuthenticatedApiClient {
        return AuthenticatedApiClient(tokenManager, httpClient, policy)
    }
}
