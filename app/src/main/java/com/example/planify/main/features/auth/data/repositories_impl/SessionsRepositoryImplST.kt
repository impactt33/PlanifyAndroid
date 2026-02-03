package com.example.planify.main.features.auth.data.repositories_impl

import com.example.planify.main.features.auth.data.dto.AuthSessionPrivateDTO
import com.example.planify.main.features.auth.domain.entities.AuthSession
import com.example.planify.main.features.auth.domain.repositories.SessionsRepository
import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionsRepositoryImplST(
    private val authenticatedApiClient: AuthenticatedApiClient
) : SessionsRepository {
    private val authFeaturePath = "/auth"

    private val logoutPath = "$authFeaturePath/logout"
    private val getActiveSessionsPath = "$authFeaturePath/sessions/active"
    private val revokeSessionPath = "$authFeaturePath/sessions/{}"
    private val revokeAllSessionsExceptCurrentPath = "$authFeaturePath/sessions/active"

    override suspend fun logout(): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            authenticatedApiClient.request(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Delete
                    url { path(logoutPath) }
                }
            )
        }
    }

    override suspend fun getActiveSessions(): Result<List<AuthSession>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val responseDto = authenticatedApiClient.request<List<AuthSessionPrivateDTO>>(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Get
                    url { path(getActiveSessionsPath) }
                }
            )

            responseDto.map { it.toEntity() }
        }
    }

    override suspend fun revokeSession(sessionUuid: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            authenticatedApiClient.request(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Delete
                    url { path(revokeSessionPath.format(sessionUuid)) }
                }
            )
        }
    }

    override suspend fun revokeAllSessionsExceptCurrent(): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            authenticatedApiClient.request(
                builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Delete
                    url { path(revokeAllSessionsExceptCurrentPath) }
                }
            )
        }
    }
}