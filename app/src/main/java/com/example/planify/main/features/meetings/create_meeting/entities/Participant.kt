package com.example.planify.main.features.meetings.create_meeting.entities

import com.example.planify.main.features.auth.domain.entities.UserPrivate
import com.example.planify.main.features.profile.entities.Profile

data class Participant(
    val user: UserPrivate,
    val profile: Profile
)
