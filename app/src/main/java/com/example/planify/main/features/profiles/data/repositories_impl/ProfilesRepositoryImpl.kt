package com.example.planify.main.features.profiles.data.repositories_impl

import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import com.example.planify.main.features.profiles.data.dto.fetch_my_profile.FetchMyProfileRequestDTO
import com.example.planify.main.features.profiles.data.dto.get_my_profile.GetMyProfileResponseDTO
import com.example.planify.main.features.profiles.data.dto.put_my_profile.PutMyProfileRequestDTO
import com.example.planify.main.features.profiles.data.dto.search.SearchProfileRequestDTO
import com.example.planify.main.features.profiles.data.dto.search.SearchProfileResponseDTO
import com.example.planify.main.features.profiles.domain.entities.Page
import com.example.planify.main.features.profiles.domain.repositories.ProfilesRepository
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.features.profiles.domain.schemas.PatchMyProfileSchema
import com.example.planify.main.features.profiles.domain.schemas.PutMyProfileSchema
import io.ktor.client.request.setBody
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

    val profileFeaturePath = "/profiles"
    val fetchMyProfilePath = "$profileFeaturePath/my"
    val patchMyProfilePath = "$profileFeaturePath/my"
    val putMyProfilePath = "$profileFeaturePath/my"
    val searchProfilePath = "$profileFeaturePath/my"



    override suspend fun fetchMyProfile(): Result<Profile> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = authenticatedApiClient.requestNotNull<GetMyProfileResponseDTO> {
                method = HttpMethod.Get
                url { path(fetchMyProfilePath) }
            }
            response.profile.toEntity()
        }
    }

    override suspend fun patchMyProfile(shema: PatchMyProfileSchema): Result<Unit> = withContext(Dispatchers.IO) {
        val requestDTO = FetchMyProfileRequestDTO(
            firstName = shema.firstName,
            lastName = shema.lastName,
            position = shema.position,
            department = shema.department,
            profileImageUrl = shema.profileImageUrl
        )

        return@withContext runCatching {
            authenticatedApiClient.requestUnit {
                method = HttpMethod.Patch
                url { path(patchMyProfilePath) }
                setBody(requestDTO)
            }
        }
    }

    override suspend fun putMyProfile(shema: PutMyProfileSchema): Result<Unit> = withContext(Dispatchers.IO){
        val requestDTO = PutMyProfileRequestDTO(
            firstName = shema.firstName,
            lastName = shema.lastName,
            position = shema.position,
            department = shema.department,
            profileImageUrl = shema.profileImageUrl
        )

        return@withContext runCatching {
            authenticatedApiClient.requestUnit {
                method = HttpMethod.Put
                url { path(putMyProfilePath) }
                setBody(requestDTO)
            }
        }
    }

    override suspend fun searchProfile(page: Int, size: Int, sort: List<String>, query: String): Result<Page> = withContext(Dispatchers.IO) {
        val requestDTO = SearchProfileRequestDTO(
            page = page,
            size = size,
            sort = sort
        )

        return@withContext runCatching {
            val response = authenticatedApiClient.requestNotNull<SearchProfileResponseDTO> {
                method = HttpMethod.Get
                url { path(searchProfilePath) }
                setBody(requestDTO)
            }

            response.result.toEntity()
        }
    }
}