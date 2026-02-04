package com.example.planify.core.data.serializers

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDateTime

val jsonCore = Json {
    serializersModule = SerializersModule {
        contextual(LocalDateTime::class) { LocalDateTimeSerializer }
    }
    ignoreUnknownKeys = true
}
