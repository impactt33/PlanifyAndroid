package com.example.planify.main.features.auth.data.repositories_impl

import com.example.planify.main.common.network.ApiClient
import com.example.planify.main.features.auth.data.dto.login.LoginRequestDTO
import com.example.planify.main.features.auth.data.dto.login.LoginResponseDTO
import com.example.planify.main.features.auth.data.dto.refresh.RefreshRequestDTO
import com.example.planify.main.features.auth.data.dto.refresh.RefreshResponseDTO
import com.example.planify.main.features.auth.data.dto.register.RegisterRequestDTO
import com.example.planify.main.features.auth.data.dto.register.RegisterResponseDTO
import com.example.planify.main.features.auth.domain.entities.AuthContext
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult
import com.example.planify.main.features.auth.domain.repositories.AuthRepository
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImplST(
    private val apiClient: ApiClient
) : AuthRepository {
    private val authFeaturePath = "/auth"

    private val registerPath = "$authFeaturePath/register"
    private val loginPath = "$authFeaturePath/login"
    private val refreshPath = "$authFeaturePath/refresh"

    override suspend fun register(username: String, email: String, password: String): Result<LoginResult> = withContext(Dispatchers.IO) {
        val requestDto = RegisterRequestDTO(
            username = username,
            email = email,
            password = password
        )

        return@withContext runCatching {
            val responseDto = apiClient.request<RegisterResponseDTO>(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Post
                    url { path(registerPath) }
                    setBody(requestDto)
                }
            )

            LoginResult(
                authContext = AuthContext(
                    session = responseDto.session.toEntity(),
                    user = responseDto.user.toEntity(),
                    accessInfo = responseDto.accessInfo.toEntity()
                ),
                tokens = responseDto.tokens.toEntity()
            )
        }
    }

    override suspend fun login(email: String, password: String): Result<LoginResult> = withContext(Dispatchers.IO) {
        val requestDto = LoginRequestDTO(
            email = email,
            password = password
        )

        return@withContext runCatching {
            val responseDto = apiClient.request<LoginResponseDTO>(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Post
                    url { path(loginPath) }
                    setBody(requestDto)
                }
            )

            LoginResult(
                authContext = AuthContext(
                    session = responseDto.session.toEntity(),
                    user = responseDto.user.toEntity(),
                    accessInfo = responseDto.accessInfo.toEntity()
                ),
                tokens = responseDto.tokens.toEntity()
            )
        }
    }

    override suspend fun refresh(refreshToken: String): Result<AuthTokenPair> = withContext(Dispatchers.IO) {
        val requestDto = RefreshRequestDTO(
            refreshToken = refreshToken
        )

        return@withContext runCatching {
            val responseDto = apiClient.request<RefreshResponseDTO>(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Post
                    url { path(refreshPath) }
                    setBody(requestDto)
                }
            )

            AuthTokenPair(
                accessToken = responseDto.accessToken,
                refreshToken = responseDto.refreshToken
            )
        }
    }
}
