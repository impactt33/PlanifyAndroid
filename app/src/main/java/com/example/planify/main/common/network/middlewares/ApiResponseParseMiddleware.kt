package com.example.planify.main.common.network.middlewares

import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddleware
import com.example.planify.core.network.middleware.NextMiddlewareCall
import com.example.planify.core.network.middleware.RequestCall
import com.example.planify.main.common.entities.ApiResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

class ApiResponseParseMiddleware<T> : ApiClientMiddleware<HttpResponse, ApiResponse<T>> {
    override suspend fun proceed(
        context: ApiRequestContext,
        request: RequestCall<HttpResponse>,
        next: NextMiddlewareCall<HttpResponse, ApiResponse<T>>
    ): ApiResponse<T> {
        return request().body<ApiResponse<T>>()
    }
}