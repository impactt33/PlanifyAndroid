package com.example.planify.main.features.firebase_fcm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenSentRequestDTO (
    @SerialName("token")
    val token: String,

    @SerialName("platform")
    val platform: String = "android"
)