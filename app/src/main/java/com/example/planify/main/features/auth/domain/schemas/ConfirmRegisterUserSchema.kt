package com.example.planify.main.features.auth.domain.schemas

data class ConfirmRegisterUserSchema (
    val verificationUserId: String,
    val verificationCode: String
)