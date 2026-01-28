package com.example.planify.main.features.home.ui.home_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planify.main.common.entities.getWeekDays
import com.example.planify.main.common.themes.Locals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.text.toLong


@Composable
fun WeeklySchedule(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val initialPage = 500
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { 1000 }
    )

    val monthName = LocalDate.now()
        .plusWeeks((pagerState.currentPage - initialPage).toLong())
        .month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        .replaceFirstChar { it.uppercase() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
            ) { page ->
                val weekDays = remember(page) { getWeekDays(page - initialPage) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    weekDays.forEach { day ->
                        DayCard(
                            modifier = Modifier
                                .weight(1f)
                                .height(Locals.dimens.dayCardHeight),
                            day = day,
                            isSelected = day.date == selectedDate,
                            onClick = { onDateSelected(day.date) }
                        )
                    }
                }
            }
        }
    }
}
