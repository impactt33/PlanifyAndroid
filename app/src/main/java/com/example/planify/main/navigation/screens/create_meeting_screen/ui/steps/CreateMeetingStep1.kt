package com.example.planify.main.navigation.screens.create_meeting_screen.ui.steps

import DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CalendarBlank
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMeetingStep1() {
    val colors = MaterialTheme.colorScheme

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var placeOrLink by remember { mutableStateOf("") }

    val formatter = remember {
        DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    }

    var date by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val utc = ZoneOffset.UTC

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Locals.spacing.m),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.step1_main_info),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground
        )
        LabeledField(
            label = stringResource(R.string.step1_name_of_meeting),
            value = title,
            onValueChange = { title = it },
            placeholder = stringResource(R.string.step1_name_of_meeting_placeholder)
        )
        LabeledField(
            label = stringResource(R.string.step1_desc),
            value = description,
            onValueChange = { description = it },
            placeholder = stringResource(R.string.step1_desc_placeholder)
        )
        LabeledField(
            label = stringResource(R.string.step1_place),
            value = placeOrLink,
            onValueChange = { placeOrLink = it },
            placeholder = stringResource(R.string.step1_place_placeholder)
        )
        Text(
            text = stringResource(R.string.step1_date),
            style = MaterialTheme.typography.labelLarge,
            color = colors.onBackground,
            fontWeight = FontWeight.Medium
        )
        Box(
            Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = date.format(formatter),
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                enabled = false,
                leadingIcon = { Icon(PhosphorIcons.Regular.CalendarBlank, null) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = colors.surface,
                    disabledLeadingIconColor = Locals.extras.mutedForeground,
                    disabledTextColor = colors.onBackground,
                    disabledBorderColor = Locals.extras.border
                ),
                shape = Locals.shapes.mediumShape
            )
        }
        DatePickerDialog(
            opened = showDatePicker,
            initialDate = date,
            onDismiss = { showDatePicker = false },
            onConfirm = { picked ->
                date = picked
                showDatePicker = false
            },
            zoneId = utc
        )
    }
}




@Composable
private fun LabeledField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = colors.onBackground,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .withShapeBackground(
                    color = colors.background,
                    shape = shape
                ),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary,
                unfocusedIndicatorColor = colors.surface
            ),
            shape = shape
        )
    }
}
