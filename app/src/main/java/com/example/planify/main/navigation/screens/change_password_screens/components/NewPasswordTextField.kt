package com.example.planify.main.navigation.screens.change_password_screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Eye
import com.adamglin.phosphoricons.regular.EyeClosed
import com.example.planify.main.common.themes.Locals

@Composable
fun NewPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onNext: () -> Unit = {},
    isError: Boolean = false
) {
    val colors = MaterialTheme.colorScheme

    var isTextVisible by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (!isError) Locals.extras.border else colors.error,
        animationSpec = tween(durationMillis = 300)
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }
        ),
        visualTransformation = if (isTextVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Locals.dimens.inputTextFieldHeight)
                    .padding(horizontal = Locals.spacing.l)
                    .background(
                        shape = Locals.shapes.mediumShape,
                        color = colors.primaryContainer
                    )
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = Locals.shapes.mediumShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(horizontal = Locals.spacing.l),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Locals.spacing.s)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Locals.extras.mutedForeground
                            )
                        } else {
                            innerTextField()
                        }
                    }

                    Icon(
                        modifier = Modifier
                            .size(Locals.icons.smallPlus)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                isTextVisible = !isTextVisible
                            },
                        tint = colors.onBackground,
                        imageVector = if (isTextVisible) PhosphorIcons.Regular.Eye else PhosphorIcons.Regular.EyeClosed,
                        contentDescription = null
                    )
                }
            }
        }
    )
}