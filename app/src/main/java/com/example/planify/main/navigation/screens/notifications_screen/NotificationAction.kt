package com.example.planify.main.navigation.screens.notifications_screen

import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.profiles.domain.entities.Profile

sealed interface NotificationAction {
    data class NotificationInvite(
        val senderProfile: Profile,
        val meetingContext: MeetingContext
    ): NotificationAction

    data class NotificationStatusUpdate(
        val targetProfile: Profile,
        val meetingContext: MeetingContext
    ): NotificationAction
}