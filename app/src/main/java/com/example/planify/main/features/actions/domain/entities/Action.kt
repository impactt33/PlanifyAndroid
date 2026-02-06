package com.example.planify.main.features.actions.domain.entities

data class Action<T>(
    val id: String,
    val type: String,
    val checked: Boolean,
    val data: T? = null
)
