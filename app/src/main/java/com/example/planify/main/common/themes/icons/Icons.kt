package com.example.planify.main.common.themes.icons

import androidx.compose.ui.unit.Dp
import com.example.planify.main.common.themes.dimens.Dimens

data class Icons(
    val small: Dp,
    val medium: Dp,
    val mediumPlus: Dp,
    val large: Dp
)

fun defaultIcons(dimens: Dimens) = Icons(
    small = dimens.iconSizeDp / 2,
    medium = dimens.iconSizeDp,
    mediumPlus = dimens.iconSizeDp * 1.2f,
    large = dimens.iconSizeDp * 2
)