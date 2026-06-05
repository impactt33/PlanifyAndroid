package com.example.planify.main.features.notifications.domain.entities

import com.example.planify.main.features.actions.domain.entities.Action


data class FullActionInfoEntity (
    val action: Action<*>,
    val actionData: String?
)