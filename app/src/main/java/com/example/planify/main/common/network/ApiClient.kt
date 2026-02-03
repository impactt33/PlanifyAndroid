package com.example.planify.main.common.network

import com.example.planify.core.network.ApiClientCore
import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import com.example.planify.main.common.network.middlewares.ApiResponseParseMiddleware
import com.example.planify.main.common.network.middlewares.AppCodeValidatorMiddleware
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

class ApiClient(
    httpClient: HttpClient,
    appCodeProcessingPolicy: AppCodeProcessingPolicy
) : ApiClientCore(
    httpClient
) {
    val appCodeValidatorMiddleware = AppCodeValidatorMiddleware(appCodeProcessingPolicy)

    fun <T> buildMiddlewareChain(): ApiClientMiddlewareChain {
        val apiResponseParserMiddleware = ApiResponseParseMiddleware<T>()  // Have to create it here to specify T

        return ApiClientMiddlewareChain.Builder.start()
            .then(apiResponseParserMiddleware)
            .then(appCodeValidatorMiddleware)
            .build()
    }

    suspend fun <T> request(
        builder: HttpRequestBuilder
    ) {
        val chain = buildMiddlewareChain<T>()

        processRequest<T>(
            chain = chain,
            context = ApiRequestContext()
        ) {
            performRequest(builder)
        }
    }
}
