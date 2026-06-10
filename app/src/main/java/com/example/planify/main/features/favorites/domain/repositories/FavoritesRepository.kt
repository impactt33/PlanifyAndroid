package com.example.planify.main.features.favorites.domain.repositories

import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord

interface FavoritesRepository {
    suspend fun getFavorites(): Result<List<FavoriteRecord>>

    suspend fun addFavorite(favoriteUserId: Long): Result<Unit>

    suspend fun removeFavorite(favoriteUserId: Long): Result<Unit>
}
