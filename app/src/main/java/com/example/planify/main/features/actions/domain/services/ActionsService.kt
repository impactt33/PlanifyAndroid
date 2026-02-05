package com.example.planify.main.features.actions.domain.services

import com.example.planify.main.features.actions.domain.entities.Action
import kotlinx.coroutines.flow.SharedFlow

interface ActionsService {
    val actionsFlow: SharedFlow<Action<*>>

    suspend fun fetchActions(): Result<List<Action<*>>>
}
