package com.example.planify.core.ui.dialogs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.planify.R


@Composable
fun AlertDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    GenericDialog(
        title = title,
        onDismiss = onDismiss,
        onOk = onDismiss,
        buttonCancelText = null,
        buttonOkText = stringResource(R.string.OK)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
