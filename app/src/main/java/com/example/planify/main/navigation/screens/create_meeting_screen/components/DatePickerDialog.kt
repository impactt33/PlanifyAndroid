import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.zone
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    opened: Boolean,
    initialDate: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    zoneId: ZoneId = ZoneId.systemDefault()
) {
    if (!opened) return

    val initialMillis = initialDate
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()

    val pickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    enabled = pickerState.selectedDateMillis != null,
                    onClick = {
                        val millis = pickerState.selectedDateMillis ?: return@TextButton
                        val picked = Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDate()
                        onConfirm(picked)
                    }
                ) { Text("ОК") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Отмена")
                }
            }
    ) {
        DatePicker(state = pickerState)
    }
}

