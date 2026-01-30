package com.example.planify.main.features.meeting.domain.services_impl

import com.example.planify.core.SingletonHolder
import com.example.planify.main.features.meeting.domain.repositories.MeetingRepository
import com.example.planify.main.features.meeting.domain.services.MeetingService
import com.example.planify.main.features.meeting.entities.MeetingInfo

class MeetingServiceImplST private constructor(
    val meetingRepository: MeetingRepository
): MeetingService {
    override suspend fun getMeetingsInfo(): List<MeetingInfo> {
        return meetingRepository.getMeetingsInfo()
    }

    companion object : SingletonHolder<MeetingServiceImplST, MeetingRepository>(::MeetingServiceImplST)
}