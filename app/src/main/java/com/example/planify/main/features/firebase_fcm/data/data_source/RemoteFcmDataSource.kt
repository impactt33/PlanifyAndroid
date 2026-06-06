package com.example.planify.main.features.firebase_fcm.data.data_source

interface RemoteFcmDataSource {
    suspend fun sendFcmToken(fcmToken: String): Result<Unit>

    suspend fun deleteFcmToken(fcmToken: String): Result<Unit>
}