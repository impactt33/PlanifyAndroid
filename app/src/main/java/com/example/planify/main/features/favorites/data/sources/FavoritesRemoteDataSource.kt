package com.example.planify.main.features.favorites.data.sources

import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import com.example.planify.main.features.favorites.data.dto.get_favorites.GetFavoritesResponseDTO
import io.ktor.http.HttpMethod
import io.ktor.http.path
import jakarta.inject.Inject

class FavoritesRemoteDataSource @Inject constructor(
    private val authenticatedApiClient: AuthenticatedApiClient
) {
    private val favoritesFeaturePath = "/favorites/my"

    private fun favoriteByUserIdPath(favoriteUserId: Long) = "$favoritesFeaturePath/$favoriteUserId"

    suspend fun getFavorites(): GetFavoritesResponseDTO =
        authenticatedApiClient.requestNotNull<GetFavoritesResponseDTO> {
            method = HttpMethod.Get
            url { path(favoritesFeaturePath) }
        }

    suspend fun addFavorite(favoriteUserId: Long) =
        authenticatedApiClient.requestUnit {
            method = HttpMethod.Post
            url { path(favoriteByUserIdPath(favoriteUserId)) }
        }

    suspend fun removeFavorite(favoriteUserId: Long) =
        authenticatedApiClient.requestUnit {
            method = HttpMethod.Delete
            url { path(favoriteByUserIdPath(favoriteUserId)) }
        }
}
