package com.example.planify.main.common.themes.icons

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.planify.main.common.themes.dimens.Dimens

data class Icons(
    val small: Dp,
    val smallPlus: Dp,
    val mediumLower: Dp,
    val medium: Dp,
    val mediumPlus: Dp,
    val largeLower: Dp,
    val large: Dp,
    val largePlus: Dp
)

fun defaultIcons(dimens: Dimens) = Icons(
    small = dimens.iconSize,
    smallPlus = dimens.iconSize * 2,
    mediumLower = dimens.iconSize * 3,
    medium = dimens.iconSize * 4,
    mediumPlus = dimens.iconSize * 5,
    largeLower = dimens.iconSize * 6,
    large = dimens.iconSize * 8,
    largePlus = dimens.iconSize * 10
)