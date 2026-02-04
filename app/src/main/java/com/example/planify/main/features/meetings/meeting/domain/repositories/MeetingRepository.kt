package com.example.planify.main.features.meetings.meeting.domain.repositories

import com.example.planify.main.features.meetings.meeting.domain.entities.MeetingInfo
import java.time.LocalDate

interface MeetingRepository {
    suspend fun fetchMeetingsInfo(): Map<LocalDate, List<MeetingInfo>>
}