package com.example.planify.main.features.auth.domain.services_impl

import com.example.planify.core.SingletonHolder
import com.example.planify.main.features.auth.domain.repositories.UsersRepository
import com.example.planify.main.features.auth.domain.services.UsersService
import com.example.planify.main.features.auth.domain.entities.UserPrivate

class UsersServiceImplST private constructor(
    val usersRepository: UsersRepository
) : UsersService {
    override fun getMe(): UserPrivate {
        return usersRepository.getMe()
    }

    override suspend fun fetchMe(): Result<UserPrivate> {
        return usersRepository.fetchMe()
    }

    override suspend fun fetchUsers(userIds: List<Long>): List<UserPrivate> {
        return usersRepository.fetchUsers(userIds = userIds)
    }

    override suspend fun fetchUser(userId: Long): UserPrivate {
        return usersRepository.fetchUser(userId = userId)
    }

    companion object : SingletonHolder<UsersServiceImplST, UsersRepository>(::UsersServiceImplST)
}