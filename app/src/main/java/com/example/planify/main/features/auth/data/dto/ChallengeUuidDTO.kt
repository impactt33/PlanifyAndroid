package com.example.planify.main.features.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeUuidDTO (
    @SerialName("challengeUUID")
    val challengeUUID: String
)