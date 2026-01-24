package com.example.planify.main.common.themes.dimens

import androidx.compose.ui.unit.dp

data class Dimens (
    val bottomBarHeight: Int = 96,
    val topBarHeight: Int = 112,
    val iconSize: Int = 64,
    val cornerRadius: Int = 32
) {
    val bottomBarHeightDp get() = bottomBarHeight.dp
    val topBarHeightDp get() = topBarHeight.dp
    val iconSizeDp get() = iconSize.dp
    val cornerRadiusDp get() = cornerRadius.dp
}