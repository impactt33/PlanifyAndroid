package com.example.planify.main.navigation.screens.meeting_info_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals

@Composable
private fun TimeDropdownField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    hours: IntRange,
    onHourSelected: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        Card(
            onClick = {
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Locals.dimens.buttonMeetingInboxCardHeight),
            shape = Locals.shapes.mediumShape,
            colors = CardDefaults.cardColors(
                containerColor = colors.background
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Locals.extras.border
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Locals.spacing.s),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.onBackground.copy(alpha = 0.6f)
                    )

                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onBackground
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(Locals.icons.small),
                    tint = colors.onBackground
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            hours.forEach { hour ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = hour.toHourText(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onHourSelected(hour)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TimeRangePickerRow(
    startHour: Int,
    endHour: Int,
    onStartHourSelected: (Int) -> Unit,
    onEndHourSelected: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.s)
    ) {
        TimeDropdownField(
            modifier = Modifier.weight(1f),
            title = "От",
            value = startHour.toHourText(),
            hours = 0..23,
            onHourSelected = { selectedStartHour ->
                onStartHourSelected(selectedStartHour)

                if (endHour <= selectedStartHour) {
                    onEndHourSelected(selectedStartHour + 1)
                }
            }
        )

        TimeDropdownField(
            modifier = Modifier.weight(1f),
            title = "До",
            value = endHour.toHourText(),
            hours = (startHour + 1)..24,
            onHourSelected = onEndHourSelected
        )
    }
}

private fun Int.toHourText(): String {
    return if (this != 24) "%02d:00".format(this) else "00:00"
}