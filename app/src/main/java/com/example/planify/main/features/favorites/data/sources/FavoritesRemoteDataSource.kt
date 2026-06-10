package com.example.planify.main.features.favorites.data.sources

import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import jakarta.inject.Inject

class FavoritesRemoteDataSource @Inject constructor(
    private val authenticatedApiClient: AuthenticatedApiClient
) {

}
