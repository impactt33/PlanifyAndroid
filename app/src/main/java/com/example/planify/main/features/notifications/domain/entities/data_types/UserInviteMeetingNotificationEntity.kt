package com.example.planify.main.features.notifications.domain.entities.data_types

import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.profiles.domain.entities.Profile
import kotlinx.serialization.Serializable

@Serializable
data class UserInviteMeetingNotificationEntity (
    val senderProfile: Profile,
    val meetingContext: MeetingContext
)
