package com.example.planify.core.network.middleware

import com.example.planify.core.network.ApiRequestContext


class ApiClientMiddlewareChain(
    private val middlewares: List<ApiClientMiddleware<*, *>>
) {
    @Suppress("UNCHECKED_CAST")
    private suspend fun <In, Out> executeChain(
        middlewareIndex: Int,
        context: ApiRequestContext,
        request: RequestCall<In>
    ): Out {
        return if (middlewareIndex >= middlewares.size) {
            request() as Out
        } else {
            val middleware = middlewares[middlewareIndex] as ApiClientMiddleware<In, Out>
            middleware.proceed(context, request) { nextRequest ->
                executeChain(middlewareIndex + 1, context, nextRequest)
            }
        }
    }

    suspend fun <In, Out> execute(
        request: RequestCall<In>,
        context: ApiRequestContext? = null
    ): Out {
        return executeChain(
            middlewareIndex = 0,
            context = context ?: ApiRequestContext(),
            request = request
        )
    }

    class Builder private constructor(
        private val steps: List<ApiClientMiddleware<*, *>>
    ) {
        companion object {
            fun start(): Builder = Builder(emptyList())
            fun empty(): ApiClientMiddlewareChain = Builder(emptyList()).build()
        }

        fun <In, Out> then(middleware: ApiClientMiddleware<In, Out>): Builder =
            Builder(steps + middleware)

        fun build(): ApiClientMiddlewareChain = ApiClientMiddlewareChain(steps)
    }
}
