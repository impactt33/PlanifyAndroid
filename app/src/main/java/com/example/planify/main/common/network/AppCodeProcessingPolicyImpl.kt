package com.example.planify.main.common.network

import com.example.planify.main.common.network.exceptions.AlreadyExistsHttpException
import com.example.planify.main.common.network.exceptions.NotFoundHttpException
import com.example.planify.main.common.network.exceptions.UnknownHttpException

class AppCodeProcessingPolicyImpl : AppCodeProcessingPolicy {
    override fun process(appCode: Int, message: String) {
        if (appCode in 1000..1999) return

        when (appCode) {
            2001 -> AlreadyExistsHttpException(message)
            2002 -> NotFoundHttpException(message)
            2003 -> NotFoundHttpException(message)
            else -> UnknownHttpException(message)
        }
    }
}
