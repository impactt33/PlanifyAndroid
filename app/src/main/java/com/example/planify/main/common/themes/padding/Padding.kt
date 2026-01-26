package com.example.planify.main.common.themes.padding

import androidx.compose.ui.unit.dp

data class Padding(
    val bottomNavBarIcon: Int = 6,
    val bottomNavBar: Int = 0,
    val bottomNavBarBackground: Int = 8,
    val createMeetDialog: Int = 8,
    val button: Int = 8
) {
    val bottomNavBarIconDp get() = bottomNavBarIcon.dp
    val bottomNavBarDp get() = bottomNavBar.dp
    val bottomNavBarBackgroundDp get() = bottomNavBarBackground.dp
    val createMeetDialogDp get() = createMeetDialog.dp
    val buttonDp get() = button.dp
}