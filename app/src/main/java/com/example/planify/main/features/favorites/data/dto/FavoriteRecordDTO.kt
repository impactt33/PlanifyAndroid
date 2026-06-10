package com.example.planify.main.features.favorites.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRecordDTO(
    @SerialName("userId")
    val userId: Long,

    @SerialName("favoriteUserId")
    val favoriteUserId: Long,

    @SerialName("createdAt")
    val createdAt: String
)
