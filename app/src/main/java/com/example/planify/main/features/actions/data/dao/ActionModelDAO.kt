package com.example.planify.main.features.actions.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.planify.main.features.actions.data.models.ActionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionModelDAO {
    @Upsert
    suspend fun upsert(model: ActionModel)

    @Query("SELECT * FROM actions ORDER BY id")
    suspend fun getAll(): List<ActionModel>

    @Query("SELECT * FROM actions ORDER BY id")
    fun observeAll(): Flow<List<ActionModel>>

    @Query("DELETE FROM actions WHERE id = :actionId")
    suspend fun deleteActionById(actionId: String)
}