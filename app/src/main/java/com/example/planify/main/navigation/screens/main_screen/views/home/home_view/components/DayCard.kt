@file:Suppress("DEPRECATION")

package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components.entities.CalendarDay
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.objectClickable
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val extras = Locals.extras

    val backgroundColor = if (isSelected)
        extras.secondary else colors.background

    val borderColor = extras.border

    val contentColor = if (isSelected)
        colors.primary else colors.onSurface.copy(alpha = 0.9f)

    Column(
        modifier = modifier
            .clip(RectangleShape)
            .background(backgroundColor)
            .objectClickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RectangleShape,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru")),
            style = MaterialTheme.typography.labelMedium,
            color = colors.onSurface
        )
        Text(
            text = day.date.dayOfMonth.toString(),
            style = if (isSelected) MaterialTheme.typography.labelLarge
                else MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Normal
            ),
            color = contentColor,
            fontSize = 20.sp
        )
    }
}