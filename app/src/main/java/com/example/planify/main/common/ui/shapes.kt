package com.example.planify.main.common.ui

import androidx.compose.foundation.shape.GenericShape
import kotlin.math.cos
import kotlin.math.sin

object CustomShapes {
    val StarShape = GenericShape { size, _ ->
        val outerRadius = size.minDimension / 2f
        val innerRadius = outerRadius * 0.45f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        val points = 10
        val startAngle = -90.0

        for (i in 0 until points) {
            val angle = Math.toRadians(startAngle + i * 360.0 / points)
            val radius = if (i % 2 == 0) outerRadius else innerRadius

            val x = centerX + cos(angle).toFloat() * radius
            val y = centerY + sin(angle).toFloat() * radius

            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }

        close()
    }
}
