package com.example.planify.main.common.themes.icons

import androidx.compose.ui.unit.Dp
import com.example.planify.main.common.themes.dimens.Dimens

data class Icons(
    val xsmall: Dp,
    val small: Dp,
    val smallPlus: Dp,
    val medium: Dp,
    val mediumPlus: Dp,
    val large: Dp,
    val largePlus: Dp
)

fun defaultIcons(dimens: Dimens) = Icons(
    xsmall = dimens.iconSize / 2.5f,
    small = dimens.iconSize / 2,
    smallPlus = dimens.iconSize / 1.6f,
    medium = dimens.iconSize,
    mediumPlus = dimens.iconSize * 1.2f,
    large = dimens.iconSize * 1.5f,
    largePlus = dimens.iconSize * 1.6f
)