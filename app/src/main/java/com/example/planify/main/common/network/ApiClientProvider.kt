package com.example.planify.main.common.network

object ApiClientProvider {
    fun createApiClient(): ApiClient {
        return ApiClient(
            httpClient = KtorClientProvider.createClient(),
            appCodeProcessingPolicy = AppCodeProcessingPolicyImpl()
        )
    }
}
