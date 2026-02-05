package com.example.planify.main.features.meetings.domain.services_impl

import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.repositories.MeetingsRepository
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.schemas.MeetingPatchSchema
import com.example.planify.main.features.meetings.domain.services.MeetingService
import java.time.LocalDate
import javax.inject.Inject

class MeetingServiceImpl @Inject constructor(
    val meetingsRepository: MeetingsRepository
) : MeetingService {
    override suspend fun createMeeting(schema: CreateMeetingSchema): Result<Meeting> {
        return meetingsRepository.createMeeting(schema)
    }

    override suspend fun getMeeting(meetingId: Long): Result<Meeting> {
        return meetingsRepository.getMeeting(meetingId)
    }

    override suspend fun patchMeeting(meetingId: Long, patch: MeetingPatchSchema): Result<Unit> {
        return meetingsRepository.patchMeeting(meetingId, patch)
    }

    override suspend fun fetchMyDailyMeetings(
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<Map<LocalDate, List<MeetingContext>>> {
        return meetingsRepository.fetchMyDailyMeetings(startDate, endDate)
    }

    override suspend fun fetchMyDailyMeetingsShort(startDate: LocalDate, endDate: LocalDate): Result<Map<LocalDate, Int>> {
        return meetingsRepository.fetchMyDailyMeetingsShort(startDate, endDate)
    }
}
