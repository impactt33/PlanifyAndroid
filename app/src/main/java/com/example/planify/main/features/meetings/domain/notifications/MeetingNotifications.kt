package com.example.planify.main.features.meetings.domain.notifications

import com.example.planify.main.common.notifications.NotificationDeepLinks

object MeetingNotifications {
    const val CHANNEL_ID = "meeting_notifications"
    fun notificationDeepLink(notificationId: Long) = "${NotificationDeepLinks.SCHEME}://notification/$notificationId"
}