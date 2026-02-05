package com.example.planify.main.features.meetings.data.dto.get_my_daily_meetings

import com.example.planify.main.features.meetings.data.dto.MeetingContextDTO
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GetMyDailyMeetingsDTO(
    @Contextual
    @SerialName("meetings")
    val meetings: Map<LocalDate, List<MeetingContextDTO>>  // TODO: create LocalDateSerializer::class
)
