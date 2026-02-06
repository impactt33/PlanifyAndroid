package com.example.planify.main.features.meetings.domain.entities

import com.example.planify.main.features.auth.domain.entities.UserPrivate
import com.example.planify.main.features.profiles.domain.entities.Profile

data class Participant(
    val user: UserPrivate,
    val profile: Profile
)
