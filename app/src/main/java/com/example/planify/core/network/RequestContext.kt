package com.example.planify.core.network

import io.ktor.client.request.HttpRequestBuilder

class RequestContext(
    val builder: HttpRequestBuilder,
    var proceed: Boolean = true
) {
    fun reject(cause: Throwable): Nothing = throw cause
}
