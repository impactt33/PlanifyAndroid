package com.example.planify.main.features.meeting.domain.services

import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo
import java.time.LocalDate

interface MeetingService {
    suspend fun getMeetingsInfo(): Map<LocalDate, List<MeetingInfo>>
}