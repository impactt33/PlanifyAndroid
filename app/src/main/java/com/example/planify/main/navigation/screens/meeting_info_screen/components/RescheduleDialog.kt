package com.example.planify.main.navigation.screens.meeting_info_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Calendar
import com.adamglin.phosphoricons.regular.Cross
import com.adamglin.phosphoricons.regular.X
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.navigation.screens.create_meeting_screen.components.RowWith2Buttons
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RescheduleDialog(
    visible: Boolean,
    meetingTitle: String,
    oldDateTime: String,
    selectedDate: LocalDate,
    startHour: Int,
    endHour: Int,
    onDateSelected: (LocalDate) -> Unit,
    onStartHourSelected: (Int) -> Unit,
    onEndHourSelected: (Int) -> Unit,
    onMoveClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!visible) return

    val colors = MaterialTheme.colorScheme

    var showDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Locals.spacing.l),
            shape = Locals.shapes.mediumShape,
            colors = CardDefaults.cardColors(
                containerColor = colors.background
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = Locals.spacing.xs
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Locals.spacing.l)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Перенести встречу",
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.onBackground
                        )

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(Locals.icons.smallPlus)
                        ) {
                            Icon(
                                imageVector = PhosphorIcons.Regular.X,
                                contentDescription = null,
                                tint = colors.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Locals.spacing.xxs))

                Text(
                    text = "Выберите новую дату и время",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground
                )

                Spacer(modifier = Modifier.height(Locals.spacing.l))

                MeetingInfoCard(
                    title = meetingTitle,
                    dateTime = oldDateTime
                )

                Spacer(modifier = Modifier.height(Locals.spacing.s))

                DialogActionRow(
                    title = "Новая дата",
                    value = selectedDate.toRussianDateText(),
                    icon = PhosphorIcons.Regular.Calendar,
                    onClick = {
                        showDatePicker = true
                    }
                )

                Spacer(modifier = Modifier.height(Locals.spacing.xs))

                TimeRangePickerRow(
                    startHour = startHour,
                    endHour = endHour,
                    onStartHourSelected = onStartHourSelected,
                    onEndHourSelected = onEndHourSelected
                )

                Spacer(modifier = Modifier.height(Locals.spacing.s))

                GradientButton(
                    text = "Перенести",
                    onClick = onMoveClick
                )

                Spacer(modifier = Modifier.height(Locals.spacing.xs))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Locals.dimens.buttonMeetingInboxCardHeight),
                    shape = Locals.shapes.smallShape,
                    border = BorderStroke(
                        width = 1.dp,
                        brush = Locals.gradients.blue
                    )
                ) {
                    Text(
                        text = "Отмена",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            brush = Locals.gradients.blue
                        )
                    )
                }
            }
        }
    }

    RescheduleDatePickerDialog(
        visible = showDatePicker,
        initialDate = selectedDate,
        onDateSelected = { date ->
            onDateSelected(date)
            showDatePicker = false
        },
        onDismiss = {
            showDatePicker = false
        }
    )
}

private fun LocalDate.toRussianDateText(): String {
    val formatter = DateTimeFormatter.ofPattern(
        "d MMMM, EEEE",
        Locale("ru")
    )

    return this.format(formatter)
}