package com.example.planify.main.common.network.middlewares

import android.util.Log
import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddleware
import io.ktor.client.request.HttpRequestBuilder

class ExceptionDetailMiddleware : ApiClientMiddleware<HttpRequestBuilder, Any?> {
    override suspend fun proceed(
        context: ApiRequestContext,
        next: suspend (HttpRequestBuilder) -> Any?,
        input: HttpRequestBuilder
    ): Any? {
        try {
            Log.i("ExceptionDetailMiddleware", "Requesting: ${input.url}")
            return next(input)
        } catch (error: Exception) {
            Log.e("ExceptionDetailMiddleware", "Got unexpected exception during requesting ${input.method} - ${input.url}: ${error::class.simpleName}: ${error.message}")
            throw error
        }
    }
}
