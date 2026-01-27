package com.example.planify.main.features.home.ui.home_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.example.planify.main.common.entities.getWeekDays
import com.example.planify.main.common.themes.Locals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


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

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val scope = rememberCoroutineScope()

        val monthName = LocalDate.now()
            .plusWeeks((pagerState.currentPage - initialPage).toLong())
            .month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            .replaceFirstChar { it.uppercase() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Locals.spacing.m),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = Locals.spacing.m),
                text = monthName,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(
                        pagerState.currentPage - 1
                    ) }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(
                        pagerState.currentPage + 1
                    ) }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Locals.spacing.m),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
                pageSpacing = Locals.spacing.s
            ) { page ->
                val weekDays = remember(page) { getWeekDays(page - initialPage) }

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weekDays.forEach { day ->
                        DayCard(
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
