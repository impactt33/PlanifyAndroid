package com.example.planify.main.navigation.screens.favorites_screen.entities

import com.example.planify.main.features.profiles.domain.entities.Profile

data class FavoriteRecordUIEntity(
    val favoriteUserProfile: Profile,
    val createdAt: String,
    val userId: Long,
    val starred: Boolean
)
