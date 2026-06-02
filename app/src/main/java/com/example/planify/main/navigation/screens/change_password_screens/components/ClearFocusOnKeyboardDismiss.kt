package com.example.planify.main.navigation.screens.change_password_screens.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun ClearFocusOnKeyboardDismiss() {
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current

    var keyboardWasVisible by remember { mutableStateOf(false) }

    val isKeyboardVisible = WindowInsets.ime.getBottom(density) > 0

    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible) {
            keyboardWasVisible = true
        } else if (keyboardWasVisible) {
            focusManager.clearFocus()
            keyboardWasVisible = false
        }
    }
}