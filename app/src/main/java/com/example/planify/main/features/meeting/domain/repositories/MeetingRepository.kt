package com.example.planify.main.features.meeting.domain.repositories

import com.example.planify.main.features.meeting.entities.MeetingInfo
import java.time.LocalDate

interface MeetingRepository {
    suspend fun getMeetingsInfo(): Map<LocalDate, List<MeetingInfo>>
}