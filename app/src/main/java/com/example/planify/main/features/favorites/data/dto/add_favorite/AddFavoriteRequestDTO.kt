package com.example.planify.main.features.favorites.data.dto.add_favorite

import kotlinx.serialization.Serializable

@Serializable
data class AddFavoriteRequestDTO(
    val favoriteUserId: Long
)
