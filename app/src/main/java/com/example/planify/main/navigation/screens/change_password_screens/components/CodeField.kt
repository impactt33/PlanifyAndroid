package com.example.planify.main.navigation.screens.change_password_screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals

@Composable
fun CodeField(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
    isError: Boolean = false,
    onFilled: (String) -> Unit = {}
) {
    ClearFocusOnKeyboardDismiss()

    val colors = MaterialTheme.colorScheme

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = code,
        onValueChange = { value ->
            val filtered = value
                .filter { it.isDigit() }
                .take(length)

            onCodeChange(filtered)

            if (filtered.length == length) {
                onFilled(filtered)
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Transparent
        ),
        decorationBox = { innerTextField ->
            Box {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Locals.spacing.s),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(length) { index ->
                        val char = code.getOrNull(index)?.toString()
                        val isActive = isFocused && index == code.length
                        val isFilled = char != null

                        val borderColor by animateColorAsState(
                            targetValue = when {
                                isError -> colors.error
                                isActive -> colors.primary
                                isFilled -> colors.primary.copy(alpha = 0.7f)
                                else -> Locals.extras.border
                            },
                            animationSpec = tween(durationMillis = 150)
                        )

                        Box(
                            modifier = Modifier
                                .size(
                                    width = Locals.dimens.codeBoxWidth,
                                    height = Locals.dimens.codeBoxHeight
                                )
                                .clip(Locals.shapes.mediumShape)
                                .background(colors.background)
                                .border(
                                    width = 1.5.dp,
                                    color = borderColor,
                                    shape = Locals.shapes.mediumShape
                                )
                                .clickable {
                                    focusRequester.requestFocus()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = char ?: "",
                                color = colors.onBackground,
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(1.dp)
                        .alpha(0f)
                ) {
                    innerTextField()
                }
            }
        }
    )
}