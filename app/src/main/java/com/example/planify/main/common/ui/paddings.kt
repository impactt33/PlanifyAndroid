package com.example.planify.main.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.add(padding: PaddingValues): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = this.calculateStartPadding(layoutDirection) + padding.calculateStartPadding(layoutDirection),
        top = this.calculateTopPadding() + padding.calculateTopPadding(),
        end = this.calculateEndPadding(layoutDirection) + padding.calculateEndPadding(layoutDirection),
        bottom = this.calculateBottomPadding() + padding.calculateBottomPadding()
    )
}

@Composable
fun PaddingValues.add(all: Dp = 0.dp): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = calculateStartPadding(layoutDirection) + all,
        top = calculateTopPadding() + all,
        end = calculateEndPadding(layoutDirection) + all,
        bottom = calculateBottomPadding() + all
    )
}

@Composable
fun PaddingValues.add(horizontal: Dp = 0.dp, vertical: Dp = 0.dp): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = calculateStartPadding(layoutDirection) + horizontal,
        top = calculateTopPadding() + vertical,
        end = calculateEndPadding(layoutDirection) + horizontal,
        bottom = calculateBottomPadding() + vertical
    )
}
