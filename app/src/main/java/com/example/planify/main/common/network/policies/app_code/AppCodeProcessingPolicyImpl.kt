package com.example.planify.main.common.network.policies.app_code

import com.example.planify.core.exceptions.UnauthenticatedAppError
import com.example.planify.main.common.network.exceptions.AlreadyExistsHttpException
import com.example.planify.main.common.network.exceptions.NotFoundHttpException
import com.example.planify.main.common.network.exceptions.UnknownHttpException
import com.example.planify.main.common.network.exceptions.WrongCodeException
import com.example.planify.main.features.actions.domain.exceptions.BadActionIdHttpException
import jakarta.inject.Inject
class AppCodeProcessingPolicyImpl @Inject constructor() : AppCodeProcessingPolicy {
    override fun process(appCode: Int, message: String) {
        if (appCode in AppCode.SUCCESS_RANGE) return

        when (appCode) {
            AppCode.ALREADY_EXISTS -> throw AlreadyExistsHttpException(message)
            AppCode.NOT_FOUND, AppCode.NOT_FOUND_ALT -> throw NotFoundHttpException(message)
            AppCode.WRONG_CODE -> throw WrongCodeException(message)
            AppCode.BAD_ACTION_ID -> throw BadActionIdHttpException(message)

            in AppCode.ACCESS_TOKEN_EXPIRED, in AppCode.REFRESH_REQUIRED ->
                throw UnauthenticatedAppError(message)

            else -> throw UnknownHttpException(message)
        }
    }

    private object AppCode {
        val SUCCESS_RANGE = 1000..1999

        const val ALREADY_EXISTS = 2001
        const val NOT_FOUND = 2002
        const val NOT_FOUND_ALT = 2003
        const val WRONG_CODE = 3015
        const val BAD_ACTION_ID = 6100

        val ACCESS_TOKEN_EXPIRED = 3005..3008
        val REFRESH_REQUIRED = 3010..3012
    }
}