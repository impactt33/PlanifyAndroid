package com.example.planify.main.features.firebase_fcm.data.repositories_impl

import com.example.planify.main.features.auth.data.sources.AuthRemoteDataSource
import com.example.planify.main.features.firebase_fcm.data.data_source.RemoteFcmDataSource
import com.example.planify.main.features.firebase_fcm.domain.repositories.FcmRepository
import jakarta.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteFcmDataSource
): FcmRepository {
    override suspend fun sendFcmToken(fcmToken: String): Result<Unit> {
        return remoteDataSource.sendFcmToken(fcmToken)
    }

    override suspend fun deleteFcmToken(fcmToken: String): Result<Unit> {
        return remoteDataSource.deleteFcmToken(fcmToken)
    }
}