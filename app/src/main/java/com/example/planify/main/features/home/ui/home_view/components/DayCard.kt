package com.example.planify.main.features.home.ui.home_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.entities.CalendarDay
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.objectClickable
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayCard(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typo = MaterialTheme.typography

    val backgroundColor = if (isSelected)
        colors.primary else Color.Transparent

    val contentColor = if (isSelected)
        colors.onPrimary else colors.onSurface

    Column(
        modifier = Modifier
            .width(Locals.dimens.dayCardWidth)
            .clip(Locals.shapes.mediumShape)
            .background(backgroundColor)
            .objectClickable(onClick = onClick)
            .padding(vertical = Locals.spacing.xxs),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            style = typo.labelMedium,
            color = if (isSelected) contentColor else Color.Gray
        )
        Text(
            text = day.date.dayOfMonth.toString(),
            style = typo.titleMedium,
            color = contentColor,
            fontWeight = FontWeight.Bold
        )
    }
}