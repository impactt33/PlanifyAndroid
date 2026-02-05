package com.example.planify.main.features.profiles.domain.services_impl

import com.example.planify.main.features.profiles.domain.repositories.ProfilesRepository
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import com.example.planify.main.features.profiles.domain.entities.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfilesServiceImpl @Inject constructor(
    val profilesRepository: ProfilesRepository
): ProfilesService {
    override suspend fun fetchMyProfile(): Result<Profile> {
        return profilesRepository.fetchMyProfile()
    }
}