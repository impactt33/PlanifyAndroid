package com.example.planify.main.features.profiles.domain.repositories

import androidx.room.RawQuery
import com.example.planify.main.features.profiles.domain.entities.Page
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.features.profiles.domain.schemas.PatchMyProfileSchema
import com.example.planify.main.features.profiles.domain.schemas.PutMyProfileSchema

interface ProfilesRepository {
    suspend fun fetchMyProfile(): Result<Profile>

    suspend fun patchMyProfile(shema: PatchMyProfileSchema): Result<Unit>

    suspend fun putMyProfile(shema: PutMyProfileSchema): Result<Unit>

    suspend fun searchProfile(page: Int, size: Int, sort: List<String>, query: String): Result<Page>
}