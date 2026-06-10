package com.example.planify.main.features.favorites.data.dto.get_favorites

import com.example.planify.main.features.favorites.data.dto.FavoriteRecordDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetFavoritesResponseDTO(
    @SerialName("favorites")
    val favorites: List<FavoriteRecordDTO> = emptyList()
)
