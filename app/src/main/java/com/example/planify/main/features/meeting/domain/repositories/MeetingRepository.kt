package com.example.planify.main.features.meeting.domain.repositories

import com.example.planify.main.features.meeting.entities.MeetingInfo

interface MeetingRepository {
    suspend fun getMeetingsInfo(): List<MeetingInfo>
}