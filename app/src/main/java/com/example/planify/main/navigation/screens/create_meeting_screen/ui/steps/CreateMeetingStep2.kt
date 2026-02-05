package com.example.planify.main.navigation.screens.create_meeting_screen.ui.steps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.create_meeting.entities.TimeSlot

@Composable
fun CreateMeetingStep2(
    slots: List<TimeSlot> = getDefaultSlots(),
    selectedId: String,
    onSelected: (TimeSlot) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                PaddingValues(
                    top = Locals.spacing.m,
                    start = Locals.spacing.m,
                    end = Locals.spacing.m
                )
            )
    ) {
        Text(
            text = stringResource(R.string.step2_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(Locals.spacing.xs))

        Text(
            text = stringResource(R.string.step2_desc),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = Locals.extras.mutedForeground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(Locals.spacing.s))

        Divider(color = Locals.extras.muted)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(Locals.spacing.s),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            slots.forEach { slot ->
                val selected = slot.id == selectedId

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TimeSlotItem(
                        slot = slot,
                        selected = selected,
                        onClick = onSelected
                    )
                }
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}


@Composable
private fun TimeSlotItem(
    slot: TimeSlot,
    selected: Boolean,
    onClick: (TimeSlot) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    val borderColor = when {
        selected -> colors.primary
        slot.isBusy -> Locals.extras.border
        else -> Locals.extras.border.copy(alpha = 0.5f)
    }

    val containerColor = when {
        slot.isBusy -> colors.surfaceVariant.copy(alpha = 0.55f)
        else -> colors.surface
    }

    val textColor = when {
        selected -> colors.primary
        slot.isBusy -> Locals.extras.mutedForeground.copy(alpha = 0.55f)
        else -> colors.onBackground
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.createMeetingTimeSlotHeight)
            .clip(shape)
            .background(containerColor)
            .clickable(
                enabled = !slot.isBusy,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick(slot) },
        color = containerColor,
        shape = shape,
        border = BorderStroke(
            width = if (selected) 1.6f.dp else 1.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Locals.spacing.s),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = slot.label,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )

            Spacer(Modifier.weight(1f))

            if (slot.isBusy) {
                Text(
                    text = "Занято",
                    style = MaterialTheme.typography.labelSmall,
                    color = Locals.extras.mutedForeground.copy(alpha = 0.65f)
                )
            }
        }
    }
}

private fun getDefaultSlots(): List<TimeSlot> = listOf(
        TimeSlot("07", "07:00 - 08:00", isBusy = false),
        TimeSlot("08", "08:00 - 09:00", isBusy = false),
        TimeSlot("09", "09:00 - 10:00", isBusy = true),
        TimeSlot("10", "10:00 - 11:00", isBusy = false),
        TimeSlot("11", "11:00 - 12:00", isBusy = false),
        TimeSlot("12", "12:00 - 13:00", isBusy = false),
        TimeSlot("13", "13:00 - 14:00", isBusy = false),
        TimeSlot("14", "14:00 - 15:00", isBusy = true),
        TimeSlot("15", "15:00 - 16:00", isBusy = false),
        TimeSlot("16", "16:00 - 17:00", isBusy = false),
        TimeSlot("17", "17:00 - 18:00", isBusy = false),
        TimeSlot("18", "18:00 - 19:00", isBusy = false),
        TimeSlot("19", "19:00 - 20:00", isBusy = false),
        TimeSlot("20", "20:00 - 21:00", isBusy = false),
        TimeSlot("21", "21:00 - 22:00", isBusy = false),
        TimeSlot("22", "22:00 - 23:00", isBusy = false),
        TimeSlot("23", "23:00 - 0:00", isBusy = false),
    )