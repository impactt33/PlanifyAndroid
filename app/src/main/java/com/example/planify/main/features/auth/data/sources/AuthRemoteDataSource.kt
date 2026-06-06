package com.example.planify.main.features.auth.data.sources

import com.example.planify.main.features.auth.domain.entities.AuthContext
import com.example.planify.main.features.auth.domain.entities.AuthTokenPair
import com.example.planify.main.features.auth.domain.entities.LoginResult
import com.example.planify.main.features.auth.domain.schemas.ConfirmRegisterUserSchema
import com.example.planify.main.features.auth.domain.schemas.RegisterUserSchema

interface AuthRemoteDataSource {
    suspend fun register(shema: RegisterUserSchema): Result<String>
    suspend fun registerConfirmation(shema: ConfirmRegisterUserSchema): Result<LoginResult>
    suspend fun login(email: String, password: String): Result<LoginResult>
    suspend fun refresh(refreshToken: String): Result<AuthTokenPair>
    suspend fun fetchActualAuthContext(accessToken: String): Result<AuthContext>
    suspend fun sendVerificationCode(email: String): Result<String>

    suspend fun checkVerificationCode(confirmationUuid: String, verificationCode: Int): Result<Unit>

    suspend fun resetPassword(newPassword: String, challengeUUID: String): Result<Unit>

    suspend fun resendRegisterCode(confirmationUuid: String): Result<Unit>

    suspend fun resendRecoverCode(challengeUUID: String): Result<Unit>
}
