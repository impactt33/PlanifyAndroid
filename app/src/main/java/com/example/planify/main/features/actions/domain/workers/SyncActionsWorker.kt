package com.example.planify.main.features.actions.domain.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.planify.main.features.actions.data.sources.ActionsLocalDataSource
import com.example.planify.main.features.actions.domain.notifications.ActionNotificationHandler
import com.example.planify.main.features.actions.domain.services.ActionsService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncActionsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val actionsService: ActionsService,
    private val handlers: Set<@JvmSuppressWildcards ActionNotificationHandler>,
    private val dataSource: ActionsLocalDataSource
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val actions = actionsService.syncActions()
            .getOrElse {
                Log.e("SyncWorker", "syncActions failed (attempt $runAttemptCount): $it")
                return if (runAttemptCount >= MAX_ATTEMPTS) Result.failure() else Result.retry()
            }

        Log.d("SyncWorker", "Fetched ${actions.size} actions")

        actions.forEach { action ->
            if (dataSource.markActionNotifiedIfNewer(action.id)) {
                handlers.find { action.type in it.supportedTypes }?.handle(action)
            }
        }

        return Result.success()
    }

    private companion object {
        const val MAX_ATTEMPTS = 5
    }
}