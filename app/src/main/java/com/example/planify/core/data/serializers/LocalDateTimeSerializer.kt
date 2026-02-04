package com.example.planify.core.data.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val instant = Instant.parse(decoder.decodeString())
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.atZone(ZoneId.systemDefault()).toInstant()
        encoder.encodeString(instant.toString())
    }
}
