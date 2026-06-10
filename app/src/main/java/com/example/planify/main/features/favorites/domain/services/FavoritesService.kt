package com.example.planify.main.features.favorites.domain.services

import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord

interface FavoritesService {
    suspend fun getFavorites(): List<FavoriteRecord>
    suspend fun addFavorite(favoriteUserId: Long)
    suspend fun removeFavorite(favoriteUserId: Long)
}
