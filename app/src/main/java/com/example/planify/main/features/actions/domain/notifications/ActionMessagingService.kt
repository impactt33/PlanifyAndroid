package com.example.planify.main.features.actions.domain.notifications

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.example.planify.main.features.actions.domain.workers.SyncActionsWorker
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.firebase_fcm.domain.registrar.FcmTokenRegistrar
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
class ActionMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var tokenRegistrar: FcmTokenRegistrar

    @Inject
    lateinit var authService: AuthService

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onMessageReceived(message: RemoteMessage) {
        if (!authService.isAuthenticated()) {
            Log.d(TAG, "Push received while unauthenticated — ignoring")
            return
        }

        WorkManager.getInstance(applicationContext).enqueue(
            OneTimeWorkRequestBuilder<SyncActionsWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
        )
    }

    override fun onNewToken(token: String) {
        serviceScope.launch {
            Log.d(TAG, "onNewToken")
            tokenRegistrar.register(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private companion object {
        const val TAG = "ActionMessagingService"
    }
}