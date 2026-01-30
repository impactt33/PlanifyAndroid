package com.example.planify.main.features.meeting.domain.services

import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo

interface MeetingService {
    suspend fun getMeetingsInfo(): List<MeetingInfo>
}