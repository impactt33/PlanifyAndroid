package com.example.planify.main.features.auth.domain.utils.network

import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import com.example.planify.main.common.network.ApiClient
import com.example.planify.main.common.network.AppCodeProcessingPolicy
import com.example.planify.main.common.network.middlewares.AppCodeValidatorMiddleware
import com.example.planify.main.common.network.middlewares.KtorExecuteMiddleware
import com.example.planify.main.features.auth.domain.AuthTokenManager
import io.ktor.client.HttpClient

class AuthenticatedApiClient(
    tokenManager: AuthTokenManager,
    httpClient: HttpClient,
    appCodeProcessingPolicy: AppCodeProcessingPolicy
) : ApiClient(httpClient, appCodeProcessingPolicy) {
    private val authMiddleware = AuthMiddleware(tokenManager)
    private val autoRefreshTokensMiddleware = AutoRefreshTokensMiddleware(tokenManager)

    override fun <T> setupMiddlewareChain(): ApiClientMiddlewareChain.Builder {
        return super.setupMiddlewareChain<T>()
            .insertBefore(authMiddleware, KtorExecuteMiddleware::class.java)
            .insertBefore(autoRefreshTokensMiddleware, AppCodeValidatorMiddleware::class.java)
    }
}
