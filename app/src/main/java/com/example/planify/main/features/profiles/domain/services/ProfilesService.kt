package com.example.planify.main.features.profiles.domain.services

import com.example.planify.main.features.profiles.domain.entities.Profile

interface ProfilesService {
    suspend fun fetchMyProfile(): Result<Profile>
}