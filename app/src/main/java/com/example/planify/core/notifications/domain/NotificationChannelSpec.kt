package com.example.planify.core.notifications.domain

data class NotificationChannelSpec (
    val id: String,
    val name: String,
    val importance: NotificationImportance
)