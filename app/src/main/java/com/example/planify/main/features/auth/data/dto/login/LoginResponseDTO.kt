package com.example.planify.main.features.auth.data.dto.login

import com.example.planify.main.features.auth.data.dto.AccessInfoDTO
import com.example.planify.main.features.auth.data.dto.AuthSessionPrivateDTO
import com.example.planify.main.features.auth.data.dto.AuthTokenPairDTO
import com.example.planify.main.features.auth.data.dto.UserPrivateDTO

data class LoginResponseDTO(
    val user: UserPrivateDTO,
    val session: AuthSessionPrivateDTO,
    val tokens: AuthTokenPairDTO,
    val accessInfo: AccessInfoDTO
)
