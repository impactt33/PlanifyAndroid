package com.example.planify.main.features.auth.domain.repositories

import com.example.planify.main.features.auth.entities.User

interface UsersRepository {
    fun getMe(): User

    suspend fun fetchMe(): User

    suspend fun fetchUsers(userIds: List<Long>): List<User>

    suspend fun fetchUser(userId: Long): User
}