package com.example.planify.core.network

import com.example.planify.core.entities.ApiCallResult
import com.example.planify.core.network.middleware.ApiClientMiddlewareChain
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request

suspend inline fun <reified T> HttpClient.sendApiRequest(
    builder: HttpRequestBuilder,
    chain: ApiClientMiddlewareChain
): ApiCallResult<T> {
    val requestContext = RequestContext(builder = builder)
    chain.handleRequest(requestContext)

    val response = this.request(builder)
    chain.handleRequest(requestContext)

    return ApiCallResult(httpResponse = response, data = result)
}
