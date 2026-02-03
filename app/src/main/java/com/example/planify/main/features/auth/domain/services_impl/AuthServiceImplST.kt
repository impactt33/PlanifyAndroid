package com.example.planify.main.features.auth.domain.services_impl

import com.example.planify.core.SingletonHolder2
import com.example.planify.main.features.auth.domain.entities.AuthSession
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult
import com.example.planify.main.features.auth.domain.repositories.AuthRepository
import com.example.planify.main.features.auth.domain.repositories.SessionsRepository
import com.example.planify.main.features.auth.domain.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthServiceImplST private constructor(
    val authRepository: AuthRepository,
    val sessionsRepository: SessionsRepository
) : AuthService {
    private val _authStateFlow: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Loading)
    override val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    override suspend fun register(username: String, email: String, password: String): Result<LoginResult> {
        return authRepository.register(username, email, password).onSuccess { result ->
            _authStateFlow.value = AuthState.Authenticated(
                context = result.authContext,
                tokenPair = result.tokens
            )
        }
    }

    override suspend fun login(email: String, password: String): Result<LoginResult> {
        return authRepository.login(email, password).onSuccess { result ->
            _authStateFlow.value = AuthState.Authenticated(
                context = result.authContext,
                tokenPair = result.tokens
            )
        }
    }

    override suspend fun refresh(): Result<AuthTokenPair> {
        val state = _authStateFlow.value
        if (state !is AuthState.Authenticated) throw IllegalStateException("Cannot refresh tokens: unauthorized")

        return authRepository.refresh(state.tokenPair.refreshToken).onSuccess { tokenPair ->
            _authStateFlow.value = (_authStateFlow.value as AuthState.Authenticated).copy(
                tokenPair = tokenPair
            )
        }
    }

    override suspend fun logout(): Result<Unit> {
        return sessionsRepository.logout().onSuccess {
            _authStateFlow.value = AuthState.Unauthenticated
        }
    }

    override suspend fun getActiveSessions(): Result<List<AuthSession>> {
        return sessionsRepository.getActiveSessions()
    }

    override suspend fun revokeSession(sessionUuid: String): Result<Unit> {
        return sessionsRepository.revokeSession(sessionUuid).onSuccess {
            if (
                authStateFlow.value is AuthState.Authenticated &&
                sessionUuid == (authStateFlow.value as AuthState.Authenticated).context.session.uuid
            ) {
                _authStateFlow.value = AuthState.Unauthenticated
            }
        }
    }

    override suspend fun revokeAllSessionsExceptCurrent(): Result<Unit> {
        return sessionsRepository.revokeAllSessionsExceptCurrent()
    }

    companion object : SingletonHolder2<AuthServiceImplST, AuthRepository, SessionsRepository>(::AuthServiceImplST)
}
