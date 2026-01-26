package com.example.planify.main.features.create_meet_dialog.ui

import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.planify.main.common.themes.dimens.LocalDimens
import com.example.planify.main.common.themes.padding.LocalPadding
import com.example.planify.main.common.themes.shapes.LocalShapes
import com.example.planify.main.common.themes.shapes.shapes
import com.example.planify.main.common.themes.spacing.LocalSpacing

@Composable
fun CreateMeetDialogView(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val shapes = LocalShapes.current
    val padding = LocalPadding.current
    val spacing = LocalSpacing.current
    val dimens = LocalDimens.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = shapes.smallShape,
            tonalElevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(padding.createMeetDialogDp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(spacing.sDp)
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(horizontal = spacing.sDp),
                        text = "Add new meet",
                        fontSize = 36.sp
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(horizontal = spacing.sDp),
                        text = "Create new meet",
                        fontSize = 16.sp
                    )
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            modifier = Modifier
                                .width(dimens.buttonSizeCreateMeetDialogDp)
                                .padding(padding.buttonDp),
                            onClick = onConfirm,
                            shape = shapes.mediumShape
                        ) {
                            Text("Confirm")
                        }
                        Button(
                            modifier = Modifier
                                .width(dimens.buttonSizeCreateMeetDialogDp)
                                .padding(padding.buttonDp),
                            onClick = onDismiss,
                            shape = shapes.mediumShape
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}