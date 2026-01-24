package com.example.planify.main.common.themes.spacing

import androidx.compose.ui.unit.dp

data class Spacing(
    val xs: Int = 4,
    val s: Int = 8,
    val m: Int = 16,
    val l: Int = 24,
    val xl: Int = 32,
    val xxl: Int = 48
) {
    val xsDp get() = xs.dp
    val sDp get() = s.dp
    val mDp get() = m.dp
    val lDp get() = l.dp
    val xlDp get() = xl.dp
    val xxlDp get() = xxl.dp
}