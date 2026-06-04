package com.example.planify.main.features.actions.domain.notifications

import com.example.planify.main.features.actions.domain.entities.Action

interface ActionNotificationHandler {
    val supportedTypes: Set<String>
    suspend fun handle(action: Action<*>)
}