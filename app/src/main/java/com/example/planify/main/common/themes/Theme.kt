package com.example.planify.main.common.themes

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.planify.main.common.themes.dimens.Dimens
import com.example.planify.main.common.themes.dimens.LocalDimens
import com.example.planify.main.common.themes.icons.LocalIcons
import com.example.planify.main.common.themes.icons.defaultIcons
import com.example.planify.main.common.themes.padding.LocalPadding
import com.example.planify.main.common.themes.padding.Padding
import com.example.planify.main.common.themes.shapes.LocalShapes
import com.example.planify.main.common.themes.shapes.shapes
import com.example.planify.main.common.themes.spacing.LocalSpacing
import com.example.planify.main.common.themes.spacing.Spacing

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun PlanifyTheme(
    dimens: Dimens = Dimens(),
    padding: Padding = Padding(),
    spacing: Spacing = Spacing(),
    content: @Composable () -> Unit
) {
    val shapes = shapes(dimens = dimens)
    val icons = defaultIcons(dimens = dimens)

    CompositionLocalProvider(
        LocalDimens provides dimens,
        LocalShapes provides shapes,
        LocalIcons provides icons,
        LocalPadding provides padding,
        LocalSpacing provides spacing
    ) {
        content()
    }
}