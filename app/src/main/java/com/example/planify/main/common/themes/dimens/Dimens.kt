package com.example.planify.main.common.themes.dimens

import android.icu.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.planify.main.features.meetings.meeting.domain.entities.Meeting

data class Dimens(
    val bottomBarHeight: Dp = 88.dp,
    val topBarHeight: Dp = 136.dp,
    val iconSize: Dp = 36.dp,
    val cornerRadius: Dp = 16.dp,
    val upcomingEventBannerHeight: Dp = 128.dp,
    val dayCardHeight: Dp = 80.dp,
    val iconBottomBarWidth: Dp = 76.dp,
    val navIndicatorHeight: Dp = 4.dp,
    val elevation: Dp = 6.dp,
    val searchBarHeight: Dp = 36.dp,
    val blur: Dp = 18.dp,
    val topNavBarHeight: Dp = 48.dp,
    val floatingActionButtonRadius: Dp = 72.dp,
    val meetingCardHeight: Dp = 188.dp,
    val dateScrollBarHeight: Dp = 60.dp,
    val emptyTimeSlot: Dp = 60.dp,
    val calendarCellSize: Dp = 56.dp,
    val logOutButtonHeight: Dp = 56.dp,
    val profileCardHeight1: Dp = 208.dp,
    val profileCardHeight2: Dp = 244.dp,
    val typeOfMeetingCardHeight: Dp = 80.dp
)