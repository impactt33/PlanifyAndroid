package com.example.planify.main.common.themes.dimens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val bottomBarHeight: Dp = 96.dp,
    val topBarHeight: Dp = 112.dp,
    val iconSize: Dp = 64.dp,
    val cornerRadius: Dp = 16.dp,
    val upcomingEventBannerHeight: Dp = 128.dp,
    val dayCardWidth: Dp = 48.dp
)