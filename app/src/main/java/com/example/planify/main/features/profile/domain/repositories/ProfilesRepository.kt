package com.example.planify.main.features.profile.domain.repositories

import com.example.planify.main.features.profile.domain.entities.Profile

interface ProfilesRepository {
    suspend fun fetchMyProfile(): Result<Profile>
}