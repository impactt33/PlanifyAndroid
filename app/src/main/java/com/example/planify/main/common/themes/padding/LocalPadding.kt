package com.example.planify.main.common.themes.padding

import androidx.compose.runtime.staticCompositionLocalOf

val LocalPadding = staticCompositionLocalOf<Padding> {
    error("Padding not provided")
}