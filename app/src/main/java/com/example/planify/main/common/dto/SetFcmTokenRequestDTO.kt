package com.example.planify.main.common.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetFcmTokenRequestDTO (
    @SerialName("fcmToken")
    val token: String
)