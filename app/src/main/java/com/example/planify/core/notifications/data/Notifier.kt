package com.example.planify.core.notifications.data

import com.example.planify.core.notifications.domain.AppNotification

interface Notifier {
    fun show(notification: AppNotification)
    fun cancel(id: Int)
}