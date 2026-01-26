package com.example.planify.main.common.themes.dimens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

data class Dimens (
    val bottomBarHeight: Int = 96,
    val topBarHeight: Int = 112,
    val iconSize: Int = 64,
    val cornerRadius: Int = 24,
    val buttonSizeCreateMeetDialog: Int = 156
) {
    val bottomBarHeightDp get() = bottomBarHeight.dp
    val topBarHeightDp get() = topBarHeight.dp
    val iconSizeDp get() = iconSize.dp
    val cornerRadiusDp get() = cornerRadius.dp
    val buttonSizeCreateMeetDialogDp get() = buttonSizeCreateMeetDialog.dp
}

@Composable
fun rememberAdaptiveDimens(): Dimens {
    val dimens = LocalDimens.current

    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    val widthDp = with(density) {
        windowInfo.containerSize.width.toDp()
    }
    val heightDp = with(density) {
        windowInfo.containerSize.height.toDp()
    }

    val baseWidth = 360.dp

    val scaleFactor = (widthDp / baseWidth)
        .coerceIn(0.85f, 1.4f)

    return Dimens(
        bottomBarHeight = (dimens.bottomBarHeight * scaleFactor).toInt(),
        topBarHeight = (dimens.topBarHeight * scaleFactor).toInt(),
        iconSize = (dimens.iconSize * scaleFactor).toInt(),
        cornerRadius = (dimens.cornerRadius * scaleFactor).toInt()
    )
}