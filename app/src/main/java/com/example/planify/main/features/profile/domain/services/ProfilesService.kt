package com.example.planify.main.features.profile.domain.services

import com.example.planify.main.features.profile.domain.entities.Profile

interface ProfilesService {
    suspend fun fetchMyProfile(): Result<Profile>
}