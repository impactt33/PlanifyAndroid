package com.example.planify.core.network

data class ApiRequestContext(
    private val metadata: Map<String, Any> = HashMap()
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> getFromMeta(key: String): T? {
        return metadata[key] as T?
    }

    companion object {
        class Builder private constructor(
            private val metadata: MutableMap<String, Any> = HashMap()
        ) {
            fun <T: Any> meta(key: String, value: T) {
                metadata[key] = value
            }

            fun build(): ApiRequestContext {
                return ApiRequestContext(metadata = metadata)
            }
        }
    }
}
