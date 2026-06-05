package com.example.planify.main.features.notifications.data.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planify.main.features.notifications.data.models.FullActionInfoModel

interface FullActionInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFullActionInfo(fullActionInfo: FullActionInfoModel)

    @Query("DELETE FROM fullActionInfo WHERE id = :id")
    suspend fun deleteFullActionInfo(id: String)

    @Query("SELECT * FROM fullActionInfo WHERE id = :id")
    suspend fun getFullActionInfo(id: String): FullActionInfoModel

    // TODO: UPDATE
}