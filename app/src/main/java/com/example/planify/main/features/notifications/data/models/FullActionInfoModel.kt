package com.example.planify.main.features.notifications.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fullActionInfo")
data class FullActionInfoModel (
    @PrimaryKey
    val id: String,
    val actionJson: String,
    val dataJson: String
)