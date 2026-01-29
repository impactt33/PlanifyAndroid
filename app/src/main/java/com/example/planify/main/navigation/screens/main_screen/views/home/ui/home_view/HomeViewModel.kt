package com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Suppress("DEPRECATION")
class HomeViewModel : ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentWeekOffset = MutableStateFlow(0)
    val currentWeekOffset: StateFlow<Int> = _currentWeekOffset.asStateFlow()
    private val monthFormatter = DateTimeFormatter.ofPattern(
        "LLLL yyyy", Locale("ru")
    )

    fun onWeekChanged(offset: Int) {
        _currentWeekOffset.value = offset
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }
    fun getMonthTitle(offset: Int): String {
        val monthTitle = LocalDate.now()
            .plusWeeks(offset.toLong())

        return monthTitle.format(monthFormatter)
            .replaceFirstChar { it.uppercase() }
    }
}