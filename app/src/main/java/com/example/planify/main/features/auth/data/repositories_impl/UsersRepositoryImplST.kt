package com.example.planify.main.features.auth.data.repositories_impl

import com.example.planify.main.features.Network
import com.example.planify.main.features.auth.data.dto.UserDTO
import com.example.planify.main.features.auth.domain.repositories.UsersRepository
import com.example.planify.main.features.auth.entities.User
import com.example.planify.main.navigation.TempGetAccessToken
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val user = User(
    id = 123L,
    email = "123@ochko.com",
    username = "nezukoo"
)
object UsersRepositoryImplST : UsersRepository {
    override fun getMe(): User {
        return user
    }

    override suspend fun fetchMe(): Result<User> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = Network.client.get("${Network.HOST}/api/v1/users/me") {
                headers {
                    append(HttpHeaders.Authorization,
                    "Bearer ${TempGetAccessToken.accessToken}"
                    )
                }
            }
            if(response.status != HttpStatusCode.OK) {
                error("Status: ${response.status}, ${response.bodyAsText()}")
            }
//            val userDto = response.body<UserDTO>()
//            userDto.toEntity()
            user
        }
    }

    override suspend fun fetchUsers(userIds: List<Long>): List<User> {
        return listOf(user)
    }

    override suspend fun fetchUser(userId: Long): User {
        return user
    }
}