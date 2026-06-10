package com.example.planify.main.features.favorites.domain.entities

import java.time.Instant

data class FavoriteRecord(
    val userId: Long,
    val favoriteUserId: Long,
    val createdAt: Instant,
)
