package com.example.planify.main.features.favorites.data.sources

import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import com.example.planify.main.features.favorites.data.dto.add_favorite.AddFavoriteRequestDTO
import com.example.planify.main.features.favorites.data.dto.get_favorites.GetFavoritesResponseDTO
import com.example.planify.main.features.favorites.data.dto.remove_favorite.RemoveFavoriteRequestDTO
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path
import jakarta.inject.Inject

class FavoritesRemoteDataSource @Inject constructor(
    private val authenticatedApiClient: AuthenticatedApiClient
) {
    private val favoritesFeaturePath = "/favorites/my"

    private fun favoriteByUserIdPath(favoriteUserId: Long) = "$favoritesFeaturePath/$favoriteUserId"

    suspend fun getFavorites(): GetFavoritesResponseDTO {
        return authenticatedApiClient.requestNotNull<GetFavoritesResponseDTO> {
            method = HttpMethod.Get
            url { path(favoritesFeaturePath) }
        }
    }

    suspend fun addFavorite(favoriteUserId: Long) {
        val requestDTO = AddFavoriteRequestDTO(
            favoriteUserId = favoriteUserId
        )

        return authenticatedApiClient.requestUnit {
            method = HttpMethod.Post
            url { path(favoritesFeaturePath) }
            setBody(requestDTO)
        }
    }

    suspend fun removeFavorite(favoriteUserId: Long) {
        val requestDTO = RemoveFavoriteRequestDTO(
            favoriteUserId = favoriteUserId
        )

        return authenticatedApiClient.requestUnit {
            method = HttpMethod.Delete
            url { path(favoritesFeaturePath) }
            setBody(requestDTO)
        }
    }
}
