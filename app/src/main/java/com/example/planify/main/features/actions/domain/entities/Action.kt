package com.example.planify.main.features.actions.domain.entities

data class Action<T>(
    val uuid: String,
    val type: String,
    val data: T? = null
)
