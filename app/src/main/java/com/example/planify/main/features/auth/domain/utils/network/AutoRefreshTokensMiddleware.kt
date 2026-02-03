package com.example.planify.main.features.auth.domain.utils.network

import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddleware
import com.example.planify.core.network.middleware.NextMiddlewareCall
import com.example.planify.core.network.middleware.RequestCall
import com.example.planify.main.common.entities.ApiResponse
import com.example.planify.main.features.auth.domain.AuthTokenManager
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair

class AutoRefreshTokensMiddleware(
    private val tokenManager: AuthTokenManager
) : ApiClientMiddleware<ApiResponse<*>, ApiResponse<*>> {
    private suspend fun tryToRefreshTokens(): Result<AuthTokenPair> = tokenManager.refreshTokens()

    override suspend fun proceed(
        context: ApiRequestContext,
        request: RequestCall<ApiResponse<*>>,
        next: NextMiddlewareCall<ApiResponse<*>, ApiResponse<*>>
    ): ApiResponse<*> {
        val response = request()

        if (!(response.appCode in 3005..3008 || response.appCode in 3010..3012)) return next { response }

        val tokens = tryToRefreshTokens().getOrThrow()

        return next { request() }  // TODO: Update token in request header!!
    }
}
