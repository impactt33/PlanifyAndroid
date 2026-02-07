package com.example.planify.main.features.profiles.domain.services_impl

import com.example.planify.main.features.profiles.domain.entities.Page
import com.example.planify.main.features.profiles.domain.repositories.ProfilesRepository
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.features.profiles.domain.schemas.PatchMyProfileSchema
import com.example.planify.main.features.profiles.domain.schemas.PutMyProfileSchema
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfilesServiceImpl @Inject constructor(
    val profilesRepository: ProfilesRepository
): ProfilesService {
    override suspend fun fetchMyProfile(): Result<Profile> {
        return profilesRepository.fetchMyProfile()
    }

    override suspend fun patchMyProfile(shema: PatchMyProfileSchema): Result<Unit> {
        return profilesRepository.patchMyProfile(shema)
    }

    override suspend fun putMyProfile(shema: PutMyProfileSchema): Result<Unit> {
        return profilesRepository.putMyProfile(shema)
    }

    override suspend fun searchProfile(page: Int, size: Int, sort: List<String>, query: String): Result<Page> {
        return profilesRepository.searchProfile(
            page = page,
            size = size,
            sort = sort,
            query = query
        )
    }
}