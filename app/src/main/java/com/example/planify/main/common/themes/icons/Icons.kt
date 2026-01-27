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
    small = dimens.iconSize / 2,
    medium = dimens.iconSize,
    mediumPlus = dimens.iconSize * 1.2f,
    large = dimens.iconSize * 1.5f
)