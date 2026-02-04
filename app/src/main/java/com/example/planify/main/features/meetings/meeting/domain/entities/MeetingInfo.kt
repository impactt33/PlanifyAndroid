package com.example.planify.main.features.meetings.meeting.domain.entities

import com.example.planify.main.features.profile.domain.entities.Profile

data class MeetingInfo(
    val meeting: Meeting,
    val invites: List<Invite>,
    val participants: List<Profile>
)