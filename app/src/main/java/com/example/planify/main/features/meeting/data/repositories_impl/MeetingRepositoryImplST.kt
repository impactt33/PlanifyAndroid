package com.example.planify.main.features.meeting.data.repositories_impl

import com.example.planify.main.features.meeting.domain.repositories.MeetingRepository
import com.example.planify.main.features.meeting.entities.Invite
import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.features.profile.Profile
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.Duration

val meetingInfo = MeetingInfo(
    meeting = Meeting(
        title = "TestMeet",
        description = "TestMeetDesc",
        timeStart = LocalDateTime.of(
            2026, 1, 30, 14, 0
        ),
        duration = Duration.ofHours(1),
        location = "A123"
    ),
    invites = listOf(
        Invite(userId = 1, isAccepted = false),
        Invite(userId = 2, isAccepted = true),
        Invite(userId = 3, isAccepted = true)
    ),
    participants = listOf(
        Profile(
            userId = 1,
            firstName = "Oleg",
            lastName = "Ruban",
            position = "DNS consult",
            department = "manager",
            profileImageUrl = "penis123123123"
        ),
        Profile(
            userId = 2,
            firstName = "Oleg",
            lastName = "Ruban",
            position = "DNS consult",
            department = "manager",
            profileImageUrl = "penis123123123"
        ),
        Profile(
            userId = 3,
            firstName = "Oleg",
            lastName = "Ruban",
            position = "DNS consult",
            department = "manager",
            profileImageUrl = "penis123123123"
        )
    )
)

object MeetingRepositoryImplST: MeetingRepository {
    override suspend fun getMeetingsInfo(): List<MeetingInfo> {
        delay(2000)
        return listOf(meetingInfo, meetingInfo, meetingInfo, meetingInfo, meetingInfo, meetingInfo)
    }
}