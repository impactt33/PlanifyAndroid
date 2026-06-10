package com.example.planify.main.features.favorites.data.repositories_impl

import com.example.planify.main.features.favorites.data.sources.FavoritesRemoteDataSource
import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord
import com.example.planify.main.features.favorites.domain.repositories.FavoritesRepository
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val remoteDataSource: FavoritesRemoteDataSource,
    private val profileService: ProfilesService
) : FavoritesRepository {
    override suspend fun getFavorites(): Result<List<FavoriteRecord>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            remoteDataSource.getFavorites().favorites.map { dto ->
                FavoriteRecord(
                    userId = dto.userId,
                    favoriteUserId = dto.favoriteUserId,
                    favoriteUserProfile = profileService.fetchProfileById(dto.favoriteUserId).getOrThrow(),
                    createdAt = Instant.parse(dto.createdAt)
                )
            }
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
