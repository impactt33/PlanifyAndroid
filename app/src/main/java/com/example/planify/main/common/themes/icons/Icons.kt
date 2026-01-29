package com.example.planify.main.common.themes.icons

import androidx.compose.ui.unit.Dp
import com.example.planify.main.common.themes.dimens.Dimens

data class Icons(
    val extraSmall: Dp,
    val small: Dp,
    val smallPlus: Dp,
    val medium: Dp,
    val mediumPlus: Dp,
    val large: Dp,
    val largePlus: Dp
)

fun defaultIcons(dimens: Dimens) = Icons(
    extraSmall = dimens.iconSize / 3,
    small = dimens.iconSize / 2,
    smallPlus = dimens.iconSize,
    medium = dimens.iconSize * 2,
    mediumPlus = dimens.iconSize * 3,
    large = dimens.iconSize * 4,
    largePlus = dimens.iconSize * 5
)