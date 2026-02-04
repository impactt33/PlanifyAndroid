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
        val middleware = middlewares[middlewareIndex] as ApiClientMiddleware<In, Out>

        return middleware.proceed(context, request) { nextRequest ->
            executeChain(middlewareIndex + 1, context, nextRequest)
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
        private val steps: MutableList<ApiClientMiddleware<*, *>> = ArrayList()
    ) {
        companion object {
            fun start(): Builder = Builder()
            fun empty(): ApiClientMiddlewareChain = Builder().build()
        }

        fun <In, Out, T> insertBefore(middleware: ApiClientMiddleware<In, Out>, beforeClazz: Class<T>): Builder {
            val index = steps.indexOfFirst { it::class.java == beforeClazz }
            if (index == -1) throw IllegalArgumentException("Cannot insert ${middleware::class.simpleName} before ${beforeClazz.simpleName}: Not found")
            steps.add(index, middleware)
            return this
        }

        fun <In, Out> then(middleware: ApiClientMiddleware<In, Out>): Builder {
            steps.add(middleware)
            return this
        }

        fun build(): ApiClientMiddlewareChain = ApiClientMiddlewareChain(steps)
    }
}
