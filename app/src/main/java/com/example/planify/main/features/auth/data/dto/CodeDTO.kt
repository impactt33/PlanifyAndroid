package com.example.planify.main.features.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeDTO (
    @SerialName("code")
    val code: Int
)