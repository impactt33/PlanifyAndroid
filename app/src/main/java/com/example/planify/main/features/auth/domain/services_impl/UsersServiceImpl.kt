package com.example.planify.main.features.auth.domain.services_impl

import com.example.planify.main.features.auth.domain.repositories.UsersRepository
import com.example.planify.main.features.auth.domain.services.UsersService
import com.example.planify.main.features.auth.domain.entities.UserPrivate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersServiceImpl @Inject constructor(
    val usersRepository: UsersRepository
) : UsersService {

    override suspend fun fetchMe(): Result<UserPrivate> {
        return usersRepository.fetchMe()
    }
}