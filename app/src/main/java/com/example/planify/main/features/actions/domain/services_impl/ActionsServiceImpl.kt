package com.example.planify.main.features.actions.domain.services_impl

import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.repositories.ActionsRepository
import com.example.planify.main.features.actions.domain.services.ActionsService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.KSerializer

class ActionsServiceImpl @Inject constructor(
    private val actionsRepository: ActionsRepository
) : ActionsService {
    override fun observeActions(): Flow<List<Action<*>>> = actionsRepository.observeActions()

    override fun observeNewActions(): Flow<List<Action<*>>> = actionsRepository.observeNewActions()

    override suspend fun syncActions(): Result<List<Action<*>>> = actionsRepository.syncActions()

    override suspend fun deleteAction(actionId: String): Result<Unit> =
        actionsRepository.deleteAction(actionId)

    override suspend fun getAllActionLocal(): Result<List<Action<*>>> =
        actionsRepository.getAllActionLocal()

    override suspend fun <T : Any> saveActionToLocalDB(action: Action<T>, serializer: KSerializer<T>): Result<Unit> =
        actionsRepository.saveActionToLocalDB(action, serializer)
}
