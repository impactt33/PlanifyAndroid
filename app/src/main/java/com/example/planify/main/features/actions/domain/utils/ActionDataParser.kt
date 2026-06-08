    package com.example.planify.main.features.actions.domain.utils

import com.example.planify.core.data.serializers.jsonCore
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer
import java.util.concurrent.ConcurrentHashMap

    class ActionDataParser {
        private val serializers: MutableMap<String, KSerializer<*>> = ConcurrentHashMap()

        fun register(type: String, serializer: KSerializer<*>) {
            serializers[type] = serializer
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> getSerializerOrNull(type: String): KSerializer<T>? =
            serializers[type] as KSerializer<T>?

        fun deserializeOrNull(data: JsonElement, type: String): Any? =
            getSerializerOrNull<Any>(type)?.let { jsonCore.decodeFromJsonElement(it, data) }

        fun deserializeOrNull(data: String, type: String): Any? =
            getSerializerOrNull<Any>(type)?.let { jsonCore.decodeFromString(it, data) }

        fun serializeJsonElement(data: JsonElement): String =
            jsonCore.encodeToString(data)
    }

    inline fun <reified T> ActionDataParser.registerSchema(type: String) {
        register(type, serializer<T>())
    }
