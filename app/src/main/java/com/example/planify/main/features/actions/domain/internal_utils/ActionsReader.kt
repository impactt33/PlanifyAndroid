package com.example.planify.main.features.actions.domain.internal_utils

import android.util.Log
import com.example.planify.main.features.actions.domain.entities.Action
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive

typealias ActionsFetcher = suspend () -> Result<List<Action<*>>>

class ActionsReader(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val fetcher: ActionsFetcher
) {
    private val maxAttempts = 5
    private val readerJob = SupervisorJob()
    private val readerCoroutineScope = CoroutineScope(dispatcher + readerJob)

    val actionsFlow: SharedFlow<Action<*>> =
        pollingFlow()
            .shareIn(
                scope = readerCoroutineScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5_000
                ),
                replay = 0
            )

    private fun pollingFlow(): Flow<Action<*>> = callbackFlow {
        var attempts = 0

        while (currentCoroutineContext().isActive) {
            try {
                val actions = fetcher().getOrThrow()
                attempts = 0
                actions.forEach { trySend(it) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                attempts += 1

                if (attempts > maxAttempts) {
                    Log.e(this::class.simpleName, "Failed to fetch actions $maxAttempts times, stopping reader")
                    return@callbackFlow
                }

                Log.w(this::class.simpleName, "Failed to fetch actions (retrying in 2s...): ${error.message}")
                delay(2000)
            }
        }
    }
}
