package com.example.planify.core.network.middleware

import com.example.planify.core.network.ApiRequestContext

typealias RequestCall<In> = suspend () -> In
typealias NextMiddlewareCall<In, Out> = suspend (suspend () -> In) -> Out

interface ApiClientMiddleware<In, Out> {
    suspend fun proceed(
        context: ApiRequestContext,
        request: RequestCall<In>,
        next: NextMiddlewareCall<In, Out>
    ): Out
}
