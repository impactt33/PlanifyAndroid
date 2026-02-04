package com.example.planify.main.features.settings.entities

import com.example.planify.main.common.entities.ThemeId


data class LocalSettings (
    val theme: ThemeId? = ThemeId.SYSTEM,
    val notifications: Boolean? = true
)