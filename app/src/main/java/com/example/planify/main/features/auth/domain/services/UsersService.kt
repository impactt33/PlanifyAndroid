package com.example.planify.main.features.auth.domain.services

import com.example.planify.main.features.auth.domain.entities.UserPrivate

interface UsersService {
    suspend fun fetchMe(): Result<UserPrivate>
}