package com.example.planify.main.features.meetings.domain.repositories

import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.schemas.MeetingPatchSchema
import java.time.LocalDate

interface MeetingsRepository {
    suspend fun createMeeting(schema: CreateMeetingSchema): Result<Meeting>
    suspend fun getMeeting(meetingId: Long): Result<Meeting>
    suspend fun patchMeeting(meetingId: Long, patch: MeetingPatchSchema): Result<Unit>

    suspend fun fetchMyDailyMeetings(startDate: LocalDate, endDate: LocalDate): Result<Map<LocalDate, List<MeetingContext>>>
    suspend fun fetchMyDailyMeetingsShort(startDate: LocalDate, endDate: LocalDate): Result<Map<LocalDate, Int>>
}
