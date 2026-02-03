package com.example.planify.core.network.middleware

import com.example.planify.core.network.RequestContext
import com.example.planify.core.network.ResponseContext

interface ApiClientMiddleware {
    suspend fun onRequest(context: RequestContext)
    suspend fun onResponse(context: ResponseContext)
}
