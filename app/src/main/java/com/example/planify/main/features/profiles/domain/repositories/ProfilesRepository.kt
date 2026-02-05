package com.example.planify.main.features.profiles.domain.repositories

import com.example.planify.main.features.profiles.domain.entities.Profile

interface ProfilesRepository {
    suspend fun fetchMyProfile(): Result<Profile>
}