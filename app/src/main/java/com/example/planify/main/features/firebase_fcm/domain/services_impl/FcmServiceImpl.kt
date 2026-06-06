package com.example.planify.main.features.firebase_fcm.domain.services_impl

import com.example.planify.main.features.firebase_fcm.domain.repositories.FcmRepository
import com.example.planify.main.features.firebase_fcm.domain.services.FcmService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmServiceImpl @Inject constructor(
    private val fcmRepository: FcmRepository
): FcmService {
    override suspend fun sendFcmToken(fcmToken: String): Result<Unit> {
        return fcmRepository.sendFcmToken(fcmToken)
    }

    override suspend fun deleteFcmToken(fcmToken: String): Result<Unit> {
        return fcmRepository.deleteFcmToken(fcmToken)
    }

}