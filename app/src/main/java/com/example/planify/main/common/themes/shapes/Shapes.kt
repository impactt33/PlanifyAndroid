package com.example.planify.main.common.themes.shapes

import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.planify.main.common.themes.dimens.Dimens

data class Shapes(
    val bottomNavBarShape: Shape,
    val smallShape: Shape,
    val mediumShape: Shape,
    val largeShape: Shape
)

fun shapes(dimens: Dimens) = Shapes(
    bottomNavBarShape = RoundedCornerShape(
        topStart = dimens.cornerRadiusDp * 2,
        topEnd = dimens.cornerRadiusDp * 2
    ),
    smallShape = RoundedCornerShape(dimens.cornerRadiusDp / 2),
    mediumShape = RoundedCornerShape(dimens.cornerRadiusDp),
    largeShape = RoundedCornerShape(dimens.cornerRadiusDp * 2)
)

