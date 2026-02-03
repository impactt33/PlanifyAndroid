package com.example.planify.core.network.middleware

import com.example.planify.core.network.RequestContext
import com.example.planify.core.network.ResponseContext

class ApiClientMiddlewareChain(
    private val middlewares: List<ApiClientMiddleware>
) {

    suspend fun handleRequest(ctx: RequestContext) {
        for (mw in middlewares) {
            mw.onRequest(ctx)
        }
    }

    suspend fun handleResponse(ctx: ResponseContext) {
        for (mw in middlewares) {
            mw.onResponse(ctx)
        }
    }

    class Builder private constructor(
        private val steps: List<ApiClientMiddleware>
    ) {
        companion object {
            fun start(): Builder = Builder(emptyList())
        }

        fun then(middleware: ApiClientMiddleware): Builder = Builder(steps + middleware)

        fun build(): ApiClientMiddlewareChain = ApiClientMiddlewareChain(steps)
    }
}
