package com.example.planify.main.features.favorites.data.dto.remove_favorite

import kotlinx.serialization.Serializable


@Serializable
data class RemoveFavoriteRequestDTO(
    val favoriteUserId: Long
)
