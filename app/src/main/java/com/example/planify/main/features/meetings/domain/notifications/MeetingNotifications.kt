package com.example.planify.main.features.meetings.domain.notifications

import com.example.planify.main.common.notifications.NotificationDeepLinks

object MeetingNotifications {
    const val CHANNEL_ID = "meetings"

    fun meetingDeepLink(meetingId: Long): String = NotificationDeepLinks.meeting(meetingId)

    fun notificationsDeepLink(): String = NotificationDeepLinks.notifications()
}