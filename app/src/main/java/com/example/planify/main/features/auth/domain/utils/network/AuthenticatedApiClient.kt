package com.example.planify.main.features.auth.domain.utils.network

import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import com.example.planify.main.common.network.ApiClient
import com.example.planify.main.common.network.AppCodeProcessingPolicy
import com.example.planify.main.common.network.middlewares.AppCodeValidatorMiddleware
import com.example.planify.main.features.auth.domain.AuthTokenManager
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders

class AuthenticatedApiClient(
    private val tokenManager: AuthTokenManager,
    httpClient: HttpClient,
    appCodeProcessingPolicy: AppCodeProcessingPolicy
) : ApiClient(httpClient, appCodeProcessingPolicy) {
    private val authTokenPair = "Bearer"
    private val autoRefreshTokensMiddleware = AutoRefreshTokensMiddleware(tokenManager)

    private fun constructHeader(accessToken: String): String {
        return "$authTokenPair $accessToken"
    }

    override fun <T> setupMiddlewareChain(): ApiClientMiddlewareChain.Builder {
        return super.setupMiddlewareChain<T>()
            .insertBefore(AppCodeValidatorMiddleware::class.java, autoRefreshTokensMiddleware)
    }

    override suspend fun performRequest(builder: HttpRequestBuilder): HttpResponse {
        val tokens = tokenManager.getTokenPair()

        builder.apply {
            headers {
                append(HttpHeaders.Authorization, constructHeader(tokens.accessToken))
            }
        }

        return super.performRequest(builder)
    }
}
