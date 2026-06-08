package com.example.planify.main.features.actions.data.dto

import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ActionDTO(
    @SerialName("id")
    val id: String,

    @SerialName("type")
    val type: String,

    @SerialName("data")
    val data: JsonElement?
) {
    fun toEntity(actionDataParser: ActionDataParser): Action<*>? {
        if (data == null) return Action<Any>(id = id, type = type, data = null)

        val parsed = actionDataParser.deserializeOrNull(data, type) ?: return null
        return Action(id = id, type = type, data = parsed)
    }
}

