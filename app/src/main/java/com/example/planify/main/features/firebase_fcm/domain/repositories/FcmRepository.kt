package com.example.planify.main.features.firebase_fcm.domain.repositories

interface FcmRepository {
    suspend fun sendFcmToken(fcmToken: String): Result<Unit>

    suspend fun deleteFcmToken(fcmToken: String): Result<Unit>
}