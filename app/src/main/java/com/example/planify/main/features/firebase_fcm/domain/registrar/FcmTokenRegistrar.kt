package com.example.planify.main.features.firebase_fcm.domain.registrar

import android.util.Log
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.firebase_fcm.domain.services.FcmService
import com.google.firebase.messaging.FirebaseMessaging
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class FcmTokenRegistrar @Inject constructor(
    private val fcmService: FcmService,
    private val authService: AuthService,
) {
    suspend fun register(token: String) {
        if (!authService.isAuthenticated()) {
            Log.d(TAG, "Skip token registration: not authenticated")
            return
        }
        fcmService.sendFcmToken(token)
            .onFailure { Log.w(TAG, "Failed to send FCM token: ${it.message}") }
    }

    suspend fun registerCurrentToken() {
        if (!authService.isAuthenticated()) return
        runCatching { FirebaseMessaging.getInstance().token.await() }
            .onSuccess { register(it) }
            .onFailure { Log.w(TAG, "Failed to fetch FCM token: ${it.message}") }
    }

    private companion object {
        const val TAG = "FcmTokenRegistrar"
    }
}