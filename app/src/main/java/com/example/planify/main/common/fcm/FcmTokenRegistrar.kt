package com.example.planify.main.common.fcm

import com.example.planify.main.common.dto.SetFcmTokenRequestDTO
import com.example.planify.main.common.network.api_client.ApiClient
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FcmTokenRegistrar @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun registerCurrentToken(): Result<Unit> = withContext(Dispatchers.IO) {
        val request = SetFcmTokenRequestDTO(
            token = FirebaseMessaging.getInstance().token.await()
        )

        return@withContext runCatching {
            apiClient.requestUnit {
                method = HttpMethod.Post
                url { } // TODO
                setBody(request)
            }
        }
    }

    suspend fun registerNewToken(token: String): Result<Unit> = withContext(Dispatchers.IO) {
        val request = SetFcmTokenRequestDTO(
            token = token
        )

        return@withContext runCatching {
            apiClient.requestUnit {
                method = HttpMethod.Post
                url { } // TODO
                setBody(request)
            }
        }
    }

    suspend fun unregisterCurrentToken(): Result<Unit> = withContext(Dispatchers.IO) {
        val request = SetFcmTokenRequestDTO(
            token = FirebaseMessaging.getInstance().token.await()
        )

        return@withContext runCatching {
            apiClient.requestUnit {
                method = HttpMethod.Delete
                url { } // TODO
                setBody(request)
            }
        }
    }
}