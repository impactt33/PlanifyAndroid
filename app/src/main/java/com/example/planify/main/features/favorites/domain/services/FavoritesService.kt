package com.example.planify.main.features.favorites.domain.services

import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord

interface FavoritesService {
    suspend fun getFavorites(): Result<List<FavoriteRecord>>
    suspend fun addFavorite(favoriteUserId: Long): Result<Unit>
    suspend fun removeFavorite(favoriteUserId: Long): Result<Unit>
}
