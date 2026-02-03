package com.example.planify.main.features.auth.domain.services

import com.example.planify.main.features.auth.domain.entities.AuthSession
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val authStateFlow: StateFlow<AuthState>

    fun isAuthenticated(): Boolean = authStateFlow.value is AuthState.Authenticated

    suspend fun register(username: String, email: String, password: String): Result<LoginResult>
    suspend fun login(email: String, password: String): Result<LoginResult>
    suspend fun refresh(): Result<AuthTokenPair>
    suspend fun logout(): Result<Unit>
    suspend fun getActiveSessions(): Result<List<AuthSession>>
    suspend fun revokeSession(sessionUuid: String): Result<Unit>
    suspend fun revokeAllSessionsExceptCurrent(): Result<Unit>
}
