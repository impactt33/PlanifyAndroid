package com.example.planify.main.features.meetings.domain.entities

import com.example.planify.main.features.profiles.domain.entities.Profile
import kotlinx.serialization.Serializable

@Serializable
data class MeetingContext(
    val invitedUserProfiles: List<Profile>,
    val participantProfiles: List<Profile>,
    val invites: List<MeetingInvite>,
    val meeting: Meeting
)
