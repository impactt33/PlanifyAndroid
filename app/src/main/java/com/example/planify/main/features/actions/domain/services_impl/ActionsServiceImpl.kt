package com.example.planify.main.features.actions.domain.services_impl

import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.internal_utils.ActionsReader
import com.example.planify.main.features.actions.domain.repositories.ActionsRepository
import com.example.planify.main.features.actions.domain.services.ActionsService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow

class ActionsServiceImpl @Inject constructor(
    private val actionsRepository: ActionsRepository
) : ActionsService {
    private val actionsReader: ActionsReader = ActionsReader(dispatcher = Dispatchers.IO, fetcher = this::fetchActions)

    override val actionsFlow: SharedFlow<Action<*>> = actionsReader.actionsFlow

    override suspend fun fetchActions(): Result<List<Action<*>>> {
        return actionsRepository.fetchActions()
    }
}
