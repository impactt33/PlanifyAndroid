package com.example.planify.main.features.auth.domain.services

import com.example.planify.main.features.auth.domain.entities.UserPrivate

interface UsersService {
    fun getMe(): UserPrivate

    suspend fun fetchMe(): Result<UserPrivate>

    suspend fun fetchUsers(userIds: List<Long>): List<UserPrivate>

    suspend fun fetchUser(userId: Long): UserPrivate
}