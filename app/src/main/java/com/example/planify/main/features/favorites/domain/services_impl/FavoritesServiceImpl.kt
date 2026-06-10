package com.example.planify.main.features.favorites.domain.services_impl

import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord
import com.example.planify.main.features.favorites.domain.repositories.FavoritesRepository
import com.example.planify.main.features.favorites.domain.services.FavoritesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesServiceImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : FavoritesService {
    override suspend fun getFavorites(): Result<List<FavoriteRecord>> {
        return favoritesRepository.getFavorites()
    }

    override suspend fun addFavorite(favoriteUserId: Long): Result<Unit> {
        return favoritesRepository.addFavorite(favoriteUserId)
    }

    override suspend fun removeFavorite(favoriteUserId: Long): Result<Unit> {
        return favoritesRepository.removeFavorite(favoriteUserId)
    }
}
