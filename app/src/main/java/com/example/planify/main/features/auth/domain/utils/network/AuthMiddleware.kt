package com.example.planify.main.features.auth.domain.utils.network

import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddleware
import com.example.planify.core.network.middleware.NextMiddlewareCall
import com.example.planify.core.network.middleware.RequestCall
import com.example.planify.main.features.auth.domain.AuthTokenManager
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

class AuthMiddleware(
    private val tokenManager: AuthTokenManager
) : ApiClientMiddleware<HttpRequestBuilder, HttpRequestBuilder> {
    private val authTokenType = "Bearer"

    private fun constructHeader(accessToken: String): String {
        return "$authTokenType $accessToken"
    }

    override suspend fun proceed(
        context: ApiRequestContext,
        request: RequestCall<HttpRequestBuilder>,
        next: NextMiddlewareCall<HttpRequestBuilder, HttpRequestBuilder>
    ): HttpRequestBuilder {
        val tokens = tokenManager.getTokenPair()

        return request().apply {
            headers {
                append(HttpHeaders.Authorization, constructHeader(tokens.accessToken))
            }
        }
    }
}