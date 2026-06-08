package com.example.planify.main.common.notifications

object NotificationDeepLinks {
    const val SCHEME = "myapp"
    const val HOST = "planify"

    const val MEETING_PATTERN = "$SCHEME://$HOST/meeting/{meetingId}"
    const val NOTIFICATIONS_PATTERN = "$SCHEME://$HOST/notifications"

    fun meeting(meetingId: Long): String = "$SCHEME://$HOST/meeting/$meetingId"
    fun notifications(): String = "$SCHEME://$HOST/notifications"
}