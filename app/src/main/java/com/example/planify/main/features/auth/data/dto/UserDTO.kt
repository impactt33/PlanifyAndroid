package com.example.planify.main.features.auth.data.dto

import com.example.planify.main.features.auth.entities.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    @SerialName("id")
    val id: Long,
    @SerialName("email")
    val email: String,
    @SerialName("username")
    val username: String
) {
    fun toEntity(): User = User(
        id = id,
        email = email,
        username = username
    )
}