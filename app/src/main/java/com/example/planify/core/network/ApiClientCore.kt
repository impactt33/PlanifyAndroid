package com.example.planify.core.network

import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import com.example.planify.core.network.middleware.RequestCall
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

open class ApiClientCore(
    protected val httpClient: HttpClient
) {
    protected suspend fun <T> processRequest(
        chain: ApiClientMiddlewareChain,
        context: ApiRequestContext,
        request: RequestCall<HttpRequestBuilder>
    ): T {
        return chain.execute(request, context)
    }

    open suspend fun <T> request(
        context: ApiRequestContext? = null,
        chain: ApiClientMiddlewareChain? = null,
        build: HttpRequestBuilder.() -> Unit,
    ): T {
        return processRequest(
            chain = chain ?: ApiClientMiddlewareChain.Builder.empty(),
            context = context ?: ApiRequestContext()
        ) {
            val builder = HttpRequestBuilder()
            builder.apply(build)
        }
    }
}
