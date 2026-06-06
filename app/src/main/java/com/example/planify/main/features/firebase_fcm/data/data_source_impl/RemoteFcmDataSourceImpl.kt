package com.example.planify.main.features.firebase_fcm.data.data_source_impl

import android.util.Log
import com.example.planify.main.features.auth.domain.utils.network.AuthenticatedApiClient
import com.example.planify.main.features.firebase_fcm.data.data_source.RemoteFcmDataSource
import com.example.planify.main.features.firebase_fcm.data.dto.FcmTokenSentRequestDTO
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Singleton
class RemoteFcmDataSourceImpl @Inject constructor(
    private val apiClient: AuthenticatedApiClient
): RemoteFcmDataSource {
    private val fcmFeaturePath = "/fcm"

    private val tokensPath = "$fcmFeaturePath/tokens"

    private fun getDeleteTokenPathUrl(fcmToken: String): String = "$tokensPath/$fcmToken"

    override suspend fun sendFcmToken(fcmToken: String): Result<Unit> = withContext(Dispatchers.IO) {
        val requestDto = FcmTokenSentRequestDTO(
            token = fcmToken
        )

        Log.d("FCM TOKEN FROM DATASOURCE", Json.encodeToString(requestDto))

        return@withContext runCatching {
            apiClient.requestUnit {
                method = HttpMethod.Post
                url { path(tokensPath) }
                setBody(requestDto)
            }
        }
    }

    override suspend fun deleteFcmToken(fcmToken: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            apiClient.requestUnit {
                method = HttpMethod.Delete
                url { path(getDeleteTokenPathUrl(fcmToken)) }
            }
        }
    }
}