package com.example.planify.main.features.actions.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planify.main.features.actions.data.models.ActionModel

@Dao
interface ActionModelDAO {
    @Insert
    suspend fun insert(model: ActionModel)

    @Query("SELECT * FROM actions")
    suspend fun getAll(): List<ActionModel>

    @Query("DELETE FROM actions WHERE id = :actionId")
    suspend fun deleteActionById(actionId: String)
}
