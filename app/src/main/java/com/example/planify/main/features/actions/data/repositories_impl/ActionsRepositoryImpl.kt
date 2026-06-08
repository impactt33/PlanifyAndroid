package com.example.planify.main.features.actions.data.repositories_impl

import android.util.Log
import com.example.planify.main.features.actions.data.sources.ActionsLocalDataSource
import com.example.planify.main.features.actions.data.sources.ActionsRemoteDataSource
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.exceptions.BadActionIdHttpException
import com.example.planify.main.features.actions.domain.repositories.ActionsRepository
import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.delay
import kotlinx.serialization.KSerializer
import javax.inject.Inject

class ActionsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ActionsRemoteDataSource,
    private val localDataSource: ActionsLocalDataSource,
    private val actionDataParser: ActionDataParser
) : ActionsRepository {

    private val repoScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val syncPipeline: SharedFlow<List<Action<*>>> = flow {
        while (true) {
            val delta = syncActions().getOrElse {
                Log.w(TAG, "sync failed: ${it.message}")
                emptyList()
            }
            emit(delta)
            delay(POLL_INTERVAL_MS)
        }
    }.shareIn(repoScope, SharingStarted.WhileSubscribed(5_000), replay = 0)

    override fun observeNewActions(): Flow<List<Action<*>>> = syncPipeline

    override fun observeActions(): Flow<List<Action<*>>> =
        localDataSource.observeActions()
            .map { models -> models.mapNotNull { it.toEntity(actionDataParser) } }
            .combine(syncPipeline.onStart { emit(emptyList()) }) { list, _ -> list }

    override suspend fun syncActions(): Result<List<Action<*>>> = fetchActionsInternal(isRetry = false)

    private suspend fun fetchActionsInternal(isRetry: Boolean): Result<List<Action<*>>> = runCatching {
        val lastSeen = localDataSource.getLastSeenActionId()

        val dtos = remoteDataSource.fetchActions(lastSeen).getOrElse { error ->
            if (!isRetry && error is BadActionIdHttpException) {
                localDataSource.resetLastSeenActionId()
                return@runCatching fetchActionsInternal(isRetry = true).getOrThrow()
            }
            throw error
        }

        dtos.forEach { dto ->
            saveActionToLocalDB(
                id = dto.id,
                type = dto.type,
                data = dto.data?.let { actionDataParser.serializeJsonElement(it) }
            ).getOrThrow()
        }

        dtos.mapNotNull { it.toEntity(actionDataParser) }
    }

    override suspend fun deleteAction(actionId: String): Result<Unit> = runCatching {
        remoteDataSource.deleteAction(actionId).getOrThrow()
        localDataSource.deleteAction(actionId).getOrThrow()
    }

    override suspend fun getAllActionLocal(): Result<List<Action<*>>> =
        localDataSource.getAllActions().map { models ->
            models.mapNotNull { it.toEntity(actionDataParser) }
        }

    override suspend fun <T : Any> saveActionToLocalDB(action: Action<T>, serializer: KSerializer<T>): Result<Unit> =
        localDataSource.saveAction(action, serializer)
            .onSuccess { localDataSource.setLastSeenActionId(action.id) }
            .map { }

    override suspend fun saveActionToLocalDB(id: String, type: String, data: String?): Result<Unit> =
        localDataSource.saveAction(id, type, data)
            .onSuccess { localDataSource.setLastSeenActionId(id) }
            .map { }

    private companion object {
        const val TAG = "ActionsRepository"
        const val POLL_INTERVAL_MS = 10_000L
    }
}
