package com.example.planify.main.features.auth.domain.services_impl

import com.example.planify.main.features.auth.domain.AuthTokenManager
import com.example.planify.main.features.auth.domain.entities.AuthSession
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult
import com.example.planify.main.features.auth.domain.repositories.AuthRepository
import com.example.planify.main.features.auth.domain.repositories.SessionsRepository
import com.example.planify.main.features.auth.domain.services.AuthService
import jakarta.inject.Inject
import jakarta.inject.Provider
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class AuthServiceImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionsRepositoryProvider: Provider<SessionsRepository>
) : AuthService, AuthTokenManager {
    private val sessionsRepository get() = sessionsRepositoryProvider.get()

    private val _authStateFlow: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Loading)
    override val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    override fun isAuthenticated(): Boolean = authStateFlow.value is AuthState.Authenticated

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

    override fun getTokenPair(): AuthTokenPair {
        if (_authStateFlow.value !is AuthState.Authenticated) throw IllegalStateException("Cannot get auth tokens: Unauthenticated")
        return (_authStateFlow.value as AuthState.Authenticated).tokenPair
    }

    override suspend fun refreshTokens(): Result<AuthTokenPair> = refresh()
}
