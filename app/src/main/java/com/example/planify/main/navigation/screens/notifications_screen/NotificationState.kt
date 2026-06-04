package com.example.planify.main.navigation.screens.notifications_screen

import com.example.planify.core.ui.state.ResourceState

data class NotificationState(
    val actions: Map<String, ResourceState<NotificationAction>>
) {
    companion object {
        fun empty() = NotificationState(actions = emptyMap())
    }
}