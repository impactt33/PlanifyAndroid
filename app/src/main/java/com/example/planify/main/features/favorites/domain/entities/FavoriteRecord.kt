package com.example.planify.main.features.favorites.domain.entities

import com.example.planify.main.features.profiles.domain.entities.Profile
import java.time.Instant

data class FavoriteRecord(
    val userId: Long,
    val favoriteUserId: Long,
    val favoriteUserProfile: Profile,
    val createdAt: Instant,
)
