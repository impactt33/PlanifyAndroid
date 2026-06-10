package com.example.planify.main.features.favorites.data.dto

import com.example.planify.main.common.dto.ProfileDTO
import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class FavoriteRecordDTO(
    @SerialName("userId")
    val userId: Long,

    @SerialName("favoriteUserId")
    val favoriteUserId: Long,

    @SerialName("favoriteUser")
    val favoriteUser: ProfileDTO,

    @SerialName("createdAt")
    val createdAt: String
) {
    fun toEntity(): FavoriteRecord = FavoriteRecord(
        userId = userId,
        favoriteUserId = favoriteUserId,
        favoriteUserProfile = favoriteUser.toEntity(),
        createdAt = Instant.parse(createdAt)
    )
}
