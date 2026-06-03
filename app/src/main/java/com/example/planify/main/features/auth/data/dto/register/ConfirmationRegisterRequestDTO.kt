package com.example.planify.main.features.auth.data.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationRegisterRequestDTO(
    @SerialName("verificationUserId")
    val verificationUserId: String,

    @SerialName("verificationCode")
    val verificationCode: String,

    @SerialName("clientName")
    val clientName: String
)
