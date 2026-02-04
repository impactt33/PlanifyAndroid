package com.example.planify.main.features.profile.data.repositories_impl

import com.example.planify.main.features.Network
import com.example.planify.main.features.profile.domain.repositories.ProfilesRepository
import com.example.planify.main.features.profile.entities.Profile
import com.example.planify.main.navigation.TempGetAccessToken
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
val timosha = Profile(
    firstName = "Тимофей",
    lastName = "Голицын",
    position = "Минетчица",
    department = "IT",
    profileImageUrl = "https://tsx.x5static.net/i/800x800-fit/xdelivery/files/06/65/cd01b821bea03c58564fxde5dcxd.jpg"
)

object ProfilesRepositoryImplST : ProfilesRepository {
    override suspend fun fetchMyProfile(): Result<Profile> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = Network.client.get("${Network.HOST}/api/v1/profiles/my") {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${TempGetAccessToken.accessToken}"
                    )
                }
            }
            if (response.status != HttpStatusCode.OK) {
                error(response.status)
            }
            //val profileDto = response.body<ProfileDTO>()
            //profileDto.toEntity()
            timosha
        }
    }

    override fun getMyProfile(): Profile {
        return timosha
    }

    override suspend fun fetchUsersProfile(userIds: List<Long>): List<Profile> {
        return listOf(timosha)
    }

    override suspend fun fetchUserProfile(userId: Long): Profile {
        return timosha
    }
}