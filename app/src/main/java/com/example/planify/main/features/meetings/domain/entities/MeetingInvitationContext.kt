package com.example.planify.main.features.meetings.domain.entities

import com.example.planify.main.features.profiles.domain.entities.Profile

data class MeetingInvitationContext(
    val invite: MeetingInvite,
    val meeting: Meeting,
    val senderProfile: Profile,
    val targetProfile: Profile
)