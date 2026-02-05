package com.example.planify.main.features.profiles.data.repositories_impl

import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import com.example.planify.main.features.profiles.data.dto.get_my_profile.GetMyProfileResponseDTO
import com.example.planify.main.features.profiles.domain.repositories.ProfilesRepository
import com.example.planify.main.features.profiles.domain.entities.Profile
import io.ktor.http.HttpMethod
import io.ktor.http.path
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class ProfilesRepositoryImpl @Inject constructor(
    private val authenticatedApiClient: AuthenticatedApiClient
) : ProfilesRepository {
    override suspend fun fetchMyProfile(): Result<Profile> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = authenticatedApiClient.request<GetMyProfileResponseDTO> {
                method = HttpMethod.Get
                url { path("/profiles/my") }
            }
            response.profile.toEntity()
        }
    }
}