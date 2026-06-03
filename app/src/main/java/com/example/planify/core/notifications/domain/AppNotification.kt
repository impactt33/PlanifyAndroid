package com.example.planify.core.notifications.domain

data class AppNotification (
    val id: Int,
    val channelId: String,
    val title: String,
    val body: String,
    val deepLink: String? = null
)