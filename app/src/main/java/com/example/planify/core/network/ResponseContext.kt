package com.example.planify.core.network

import io.ktor.client.call.HttpClientCall
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.takeFrom
import io.ktor.client.statement.HttpResponse

class ResponseContext(
    val call: HttpClientCall,
    var response: HttpResponse
) {
    suspend fun resend(
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return call.client.request {
            takeFrom(call.request)
            block()
        }
    }
}
