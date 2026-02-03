package com.example.planify.core.network

import com.example.planify.core.network.middleware.ApiClientMiddleware
import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import com.example.planify.core.network.middleware.RequestCall
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse

open class ApiClientCore(
    protected val httpClient: HttpClient
) {
    protected suspend fun <T> processRequest(
        chain: ApiClientMiddlewareChain,
        context: ApiRequestContext,
        request: RequestCall<HttpResponse>
    ): T {
        return chain.execute(request, context)
    }

    protected open suspend fun performRequest(builder: HttpRequestBuilder): HttpResponse {
        return httpClient.request(builder)
    }

    open suspend fun <T> request(
        builder: HttpRequestBuilder,
        context: ApiRequestContext? = null
    ) {
        processRequest<T>(
            chain = ApiClientMiddlewareChain.Builder.empty(),
            context = context ?: ApiRequestContext()
        ) {
            performRequest(builder)
        }
    }
}
