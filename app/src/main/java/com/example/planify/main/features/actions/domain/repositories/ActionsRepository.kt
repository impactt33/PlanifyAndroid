package com.example.planify.main.features.actions.domain.repositories

import com.example.planify.main.features.actions.domain.entities.Action

interface ActionsRepository {
    suspend fun fetchActions(): Result<List<Action<*>>>
}
