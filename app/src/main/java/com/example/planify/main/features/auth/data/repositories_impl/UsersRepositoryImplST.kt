package com.example.planify.main.features.auth.data.repositories_impl

import com.example.planify.main.features.auth.domain.entities.UserPrivate
import com.example.planify.main.features.auth.domain.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val user = UserPrivate(
    id = 123L,
    email = "123@ochko.com",
    username = "nezukoo"
)

object UsersRepositoryImplST : UsersRepository {
    override fun getMe(): UserPrivate {
        return user
    }

    override suspend fun fetchMe(): Result<UserPrivate> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            user
        }
    }

    override suspend fun fetchUsers(userIds: List<Long>): List<UserPrivate> {
        return listOf(user)
    }

    override suspend fun fetchUser(userId: Long): UserPrivate {
        return user
    }
}