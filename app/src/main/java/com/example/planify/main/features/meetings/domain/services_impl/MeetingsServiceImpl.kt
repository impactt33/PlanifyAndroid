package com.example.planify.main.features.meetings.domain.services_impl

import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.repositories.MeetingsRepository
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.schemas.PatchMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingsServiceImpl @Inject constructor(
    private val meetingsRepository: MeetingsRepository
) : MeetingsService {
    override suspend fun fetchMeetingContext(meetingId: Long): Result<MeetingContext> {
        return meetingsRepository.fetchMeetingContext(meetingId)
    }

    override suspend fun createMeeting(schema: CreateMeetingSchema): Result<Meeting> {
        return meetingsRepository.createMeeting(schema)
    }

    override suspend fun fetchMeeting(meetingId: Long): Result<Meeting> {
        return meetingsRepository.getMeeting(meetingId)
    }

    override suspend fun patchMeeting(meetingId: Long, patch: PatchMeetingSchema): Result<Unit> {
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

    override suspend fun fetchUserSchedule(forDate: LocalDate): Result<Map<Int, Boolean>> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val meetings = fetchMyDailyMeetings(forDate, forDate).getOrThrow()[forDate]!!

            (1 until 24).associateWith { slot -> (meetings.firstOrNull { it.meeting.startsAt.hour == slot } == null) }
        }
    }
}
