package com.example.planify.main.features.settings.entities

import com.example.planify.main.common.entities.SystemTheme

data class Settings (
    val notificationsEnabled: Boolean,
    val systemTheme: SystemTheme
)