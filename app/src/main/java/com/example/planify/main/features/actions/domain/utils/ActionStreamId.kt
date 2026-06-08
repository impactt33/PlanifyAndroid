package com.example.planify.main.features.actions.domain.utils

object ActionStreamId {
    fun millis(id: String): Long {
        val streamId = id.substringAfter("===", id)
        val dash = streamId.indexOf('-')
        val millisPart = if (dash > 0) streamId.take(dash) else streamId
        return millisPart.toLongOrNull() ?: 0L
    }

    fun seq(id: String): Long {
        val streamId = id.substringAfter("===", id)
        val dash = streamId.indexOf('-')
        return if (dash > 0) streamId.substring(dash + 1).toLongOrNull() ?: 0L else 0L
    }
}