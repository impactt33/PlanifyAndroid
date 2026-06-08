package com.example.planify.main.features.actions.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.utils.ActionDataParser

@Entity(tableName = "actions")
data class ActionModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "data")
    val data: String? = null
) {
    fun toEntity(actionDataParser: ActionDataParser): Action<*>? {
        if (data == null) return Action<Any>(id = id, type = type, data = null)

        val parsed = actionDataParser.deserializeOrNull(data, type) ?: return null
        return Action(id = id, type = type, data = parsed)
    }
}
