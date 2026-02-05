package com.example.planify.main.features.auth.domain.repositories

import com.example.planify.main.features.auth.domain.entities.AuthContext
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult

interface AuthRepository {
    suspend fun register(username: String, email: String, password: String): Result<LoginResult>
    suspend fun login(email: String, password: String): Result<LoginResult>
    suspend fun refresh(refreshToken: String): Result<AuthTokenPair>

    suspend fun fetchActualAuthContext(accessToken: String): Result<AuthContext>
}
