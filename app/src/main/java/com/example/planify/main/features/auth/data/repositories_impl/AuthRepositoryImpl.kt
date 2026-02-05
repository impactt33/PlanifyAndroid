package com.example.planify.main.features.auth.data.repositories_impl

import com.example.planify.main.common.network.api_client.ApiClient
import com.example.planify.main.features.auth.data.dto.login.LoginRequestDTO
import com.example.planify.main.features.auth.data.dto.login.LoginResponseDTO
import com.example.planify.main.features.auth.data.dto.refresh.RefreshRequestDTO
import com.example.planify.main.features.auth.data.dto.refresh.RefreshResponseDTO
import com.example.planify.main.features.auth.data.dto.register.RegisterRequestDTO
import com.example.planify.main.features.auth.data.dto.register.RegisterResponseDTO
import com.example.planify.main.features.auth.domain.entities.AuthContext
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult
import com.example.planify.main.features.auth.domain.repositories.AuthRepository
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class AuthRepositoryImpl @Inject constructor(
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
            val responseDTO = apiClient.requestNotNull<RegisterResponseDTO> {
                method = HttpMethod.Post
                url { path(registerPath) }
                setBody(requestDto)
            }

            LoginResult(
                authContext = AuthContext(
                    session = responseDTO.session.toEntity(),
                    user = responseDTO.user.toEntity(),
                    accessInfo = responseDTO.accessInfo.toEntity()
                ),
                tokens = responseDTO.tokens.toEntity()
            )
        }
    }

    override suspend fun login(email: String, password: String): Result<LoginResult> = withContext(Dispatchers.IO) {
        val requestDto = LoginRequestDTO(
            email = email,
            password = password
        )

        return@withContext runCatching {
            val responseDTO = apiClient.requestNotNull<LoginResponseDTO> {
                method = HttpMethod.Post
                url { path(loginPath) }
                setBody(requestDto)
            }

            LoginResult(
                authContext = AuthContext(
                    session = responseDTO.session.toEntity(),
                    user = responseDTO.user.toEntity(),
                    accessInfo = responseDTO.accessInfo.toEntity()
                ),
                tokens = responseDTO.tokens.toEntity()
            )
        }
    }

    override suspend fun refresh(refreshToken: String): Result<AuthTokenPair> = withContext(Dispatchers.IO) {
        val requestDto = RefreshRequestDTO(
            refreshToken = refreshToken
        )

        return@withContext runCatching {
            val responseDTO = apiClient.requestNotNull<RefreshResponseDTO> {
                method = HttpMethod.Post
                url { path(refreshPath) }
                setBody(requestDto)
            }

            AuthTokenPair(
                accessToken = responseDTO.accessToken,
                refreshToken = responseDTO.refreshToken
            )
        }
    }
}
