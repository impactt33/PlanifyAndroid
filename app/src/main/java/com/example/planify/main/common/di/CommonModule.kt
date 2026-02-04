package com.example.planify.main.common.di

import android.util.Log
import com.example.planify.core.data.serializers.LocalDateTimeSerializer
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
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDateTime
import io.ktor.client.plugins.logging.Logger

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
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
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    serializersModule = SerializersModule {
                        contextual(LocalDateTime::class) { LocalDateTimeSerializer }
                    }
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }

            defaultRequest {
                host = "r2.server.universallplus.ru:8855/api/v1"  // TODO: Use env
                url { protocol = URLProtocol.HTTP }
                headers {
                    set(HttpHeaders.ContentType, "application/json")
                    set(HttpHeaders.Connection, "close")
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
