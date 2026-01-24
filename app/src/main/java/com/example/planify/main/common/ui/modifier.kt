package com.example.planify.main.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable

fun Modifier.objectClickable(onClick: () -> Unit) =
    this.clickable(
        indication = ripple(),
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )

fun Modifier.withShapeBackground(
    color: Color,
    shape: Shape
) =
    this
        .background(
            color = color,
            shape = shape
        )
        .clip(shape = shape)