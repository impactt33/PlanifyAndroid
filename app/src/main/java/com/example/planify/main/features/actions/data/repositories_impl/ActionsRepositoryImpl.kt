package com.example.planify.main.features.actions.data.repositories_impl

import com.example.planify.main.features.actions.data.internal_utils.ActionsReader
import com.example.planify.main.features.actions.data.sources.ActionsLocalDataSource
import com.example.planify.main.features.actions.data.sources.ActionsRemoteDataSource
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.repositories.ActionsRepository
import com.example.planify.main.features.actions.domain.utils.ActionDataParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.KSerializer
import javax.inject.Inject

class ActionsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ActionsRemoteDataSource,
    private val localDataSource: ActionsLocalDataSource,
    private val actionDataParser: ActionDataParser
) : ActionsRepository {
    private val actionsReader: ActionsReader = ActionsReader(dispatcher = Dispatchers.IO, fetcher = this::fetchActions, initializer = this::getAllActionLocal)

    override val actionsFlow: SharedFlow<Action<*>> = actionsReader.actionsFlow

    override suspend fun fetchActions(): Result<List<Action<*>>> {
        val lastSeen = localDataSource.getLastSeenActionId()
        return remoteDataSource.fetchActions(lastSeen).map { actions -> actions.map { it.toEntity(actionDataParser) } }
    }

    override suspend fun deleteAction(actionId: String): Result<Unit> = runCatching {
        remoteDataSource.deleteAction(actionId).getOrThrow()
        localDataSource.deleteAction(actionId).getOrThrow()
    }

    override suspend fun getAllActionLocal(): Result<List<Action<*>>> {
        return localDataSource.getAllActions().map { actions -> actions.map { it.toEntity(actionDataParser) } }
    }

    override suspend fun <T : Any> saveActionToLocalDB(action: Action<T>, serializer: KSerializer<T>): Result<Unit> {
        return localDataSource.saveAction(action, serializer)
            .onSuccess { localDataSource.setLastSeenActionId(action.id) }
            .map { }
    }
}
