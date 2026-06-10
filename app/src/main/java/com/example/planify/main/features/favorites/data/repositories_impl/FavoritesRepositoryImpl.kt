package com.example.planify.main.features.favorites.data.repositories_impl

import com.example.planify.main.features.favorites.data.sources.FavoritesRemoteDataSource
import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord
import com.example.planify.main.features.favorites.domain.repositories.FavoritesRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val remoteDataSource: FavoritesRemoteDataSource
) : FavoritesRepository {
    override suspend fun getFavorites(): Result<List<FavoriteRecord>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            remoteDataSource.getFavorites().favorites.map { it.toEntity() }
        }
    }

    override suspend fun addFavorite(favoriteUserId: Long): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            remoteDataSource.addFavorite(favoriteUserId)
        }
    }

    override suspend fun removeFavorite(favoriteUserId: Long): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            remoteDataSource.removeFavorite(favoriteUserId)
        }
    }
}
