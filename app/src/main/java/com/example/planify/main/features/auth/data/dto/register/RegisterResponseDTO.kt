package com.example.planify.main.features.auth.data.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDTO(
    @SerialName("confirmationUUID")
    val confirmationUUID: String
)