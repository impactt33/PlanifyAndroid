package com.example.planify.main.common.network.middlewares

import com.example.planify.core.network.ApiRequestContext
import com.example.planify.core.network.middleware.ApiClientMiddleware
import com.example.planify.core.network.middleware.NextMiddlewareCall
import com.example.planify.core.network.middleware.RequestCall
import com.example.planify.main.common.entities.ApiResponse
import com.example.planify.main.common.network.AppCodeProcessingPolicy

class AppCodeValidatorMiddleware(
    private val policy: AppCodeProcessingPolicy
) : ApiClientMiddleware<ApiResponse<*>, ApiResponse<*>> {
    override suspend fun proceed(
        context: ApiRequestContext,
        request: RequestCall<ApiResponse<*>>,
        next: NextMiddlewareCall<ApiResponse<*>, ApiResponse<*>>
    ): ApiResponse<*> {
        val response = request()
        policy.process(response.appCode, response.message)
        return next { response }
    }
}
