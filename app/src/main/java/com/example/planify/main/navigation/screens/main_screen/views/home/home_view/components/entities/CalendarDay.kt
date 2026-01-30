package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities

import java.time.LocalDate
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters

data class CalendarDay(
    val date: LocalDate,
    val isSelected: Boolean = false,
    val isToday: Boolean = false
)

fun getWeekDays(weekOffset: Int): List<CalendarDay> {
    val today = LocalDate.now()
    val firstDayOfWeek = today
        .plusWeeks(weekOffset.toLong())
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    return  (0..6).map { i ->
        val date = firstDayOfWeek.plusDays(i.toLong())
        CalendarDay(
            date = date,
            isToday = date == today
        )
    }
}