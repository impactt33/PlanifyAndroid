package com.example.planify.main.common.network.di

import android.util.Log
import com.example.planify.BuildConfig
import com.example.planify.core.data.serializers.jsonCore
import com.example.planify.main.common.network.api_client.ApiClient
import com.example.planify.main.common.network.policies.app_code.AppCodeProcessingPolicy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient() {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorClient", message)
                    }
                }
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(jsonCore)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }

            defaultRequest {
                host = BuildConfig.API_HOST
                url { protocol = URLProtocol.HTTP }
                headers {
                    set(HttpHeaders.ContentType, "application/json")
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideGeneralApiClient(
        httpClient: HttpClient,
        policy: AppCodeProcessingPolicy
    ): ApiClient {
        return ApiClient(httpClient, policy)
    }
}
