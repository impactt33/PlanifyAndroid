package com.example.planify.main.features.actions.domain.notifications

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.example.planify.main.features.actions.domain.workers.SyncActionsWorker
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.firebase_fcm.domain.services.FcmService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActionMessagingService(): FirebaseMessagingService() {
    @Inject
    lateinit var fcmService: FcmService

    @Inject
    lateinit var authService: AuthService

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onMessageReceived(message: RemoteMessage) {
        WorkManager.getInstance(applicationContext)
            .enqueue(
                OneTimeWorkRequestBuilder<SyncActionsWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .build()
            )
    }

    override fun onNewToken(token: String) {
        serviceScope.launch {
            if (authService.isAuthenticated()) {
                Log.d("FCM TOKEN FROM SERVICE", token)
                fcmService.sendFcmToken(token)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}