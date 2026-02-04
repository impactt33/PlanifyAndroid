package com.example.planify.main.common.network

import com.example.planify.core.network.ApiClientCore
import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import com.example.planify.main.common.network.middlewares.ApiResponseParseMiddleware
import com.example.planify.main.common.network.middlewares.AppCodeValidatorMiddleware
import com.example.planify.main.common.network.middlewares.KtorExecuteMiddleware
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

open class ApiClient(
    httpClient: HttpClient,
    appCodeProcessingPolicy: AppCodeProcessingPolicy
) : ApiClientCore(
    httpClient
) {
    private val appCodeValidatorMiddleware = AppCodeValidatorMiddleware(appCodeProcessingPolicy)
    private val ktorExecuteMiddleware = KtorExecuteMiddleware(this.httpClient)

    protected open fun <T> setupMiddlewareChain(): ApiClientMiddlewareChain.Builder {
        val apiResponseParserMiddleware = ApiResponseParseMiddleware<T>()  // Have to create it here to specify T

        return ApiClientMiddlewareChain.Builder.start()
            .then(apiResponseParserMiddleware)
            .then(appCodeValidatorMiddleware)
            .then(ktorExecuteMiddleware)
    }

    open suspend fun <T> request(
        context: ApiRequestContext? = null,
        build: HttpRequestBuilder.() -> Unit
    ): T {
        return super.request(
            context = context,
            chain = setupMiddlewareChain<T>().build(),
            build = build
        )
    }
}
