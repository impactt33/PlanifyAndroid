package com.example.planify.main.features.auth.data.dto

import com.example.planify.main.features.auth.domain.entities.AuthSession
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class AuthSessionPrivateDTO(
    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("userId")
    val userId: Long,

    @SerialName("active")
    val isActive: Boolean = true,

    @SerialName("createdAt")
    val createdAt: LocalDateTime,

    @SerialName("lastUsedAt")
    val lastUsedAt: LocalDateTime,

    @SerialName("expiresAt")
    val expiresAt: LocalDateTime
) {
    fun toEntity(): AuthSession = AuthSession(
        uuid = uuid,
        name = name,
        userId = userId,
        isActive = isActive,
        createdAt = createdAt,
        lastUsedAt = lastUsedAt,
        expiresAt = expiresAt
    )
}
