package com.example.planify.main.features.auth.data.dto.register

import com.example.planify.main.features.auth.data.dto.AccessInfoDTO
import com.example.planify.main.features.auth.data.dto.AuthSessionPrivateDTO
import com.example.planify.main.features.auth.data.dto.AuthTokenPairDTO
import com.example.planify.main.features.auth.data.dto.UserPrivateDTO

data class RegisterResponseDTO(
    val user: UserPrivateDTO,
    val session: AuthSessionPrivateDTO,
    val tokens: AuthTokenPairDTO,
    val accessInfo: AccessInfoDTO
)
