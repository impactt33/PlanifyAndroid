package com.example.planify.main.features.meetings.data.dto.get_my_daily_meetings_short

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GetMyDailyMeetingsShortDTO(
    @Contextual
    @SerialName("meetings")
    val meetings: Map<LocalDate, Int>
)
