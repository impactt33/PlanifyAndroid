package com.example.planify.main.navigation.screens.main_screen.views.home.ui.home_view.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Clock
import com.adamglin.phosphoricons.regular.MapPin
import com.adamglin.phosphoricons.regular.Users
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.themes.shapes.LocalShapes
import com.example.planify.main.common.ui.withShapeBackground

@Composable
fun MeetingCard(
    title: String,
    description: String,
    time: String,
    location: String,
    participants: String,
    modifier: Modifier = Modifier
) {
    val glass = Locals.extras.glass
    val shape = Locals.shapes.mediumShape

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = glass.bg
            )
            .shadow(
                elevation = Locals.dimens.elevation
            )
            .padding(Locals.spacing.l)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Locals.extras.foreground
            )
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Locals.extras.mutedForeground,
            lineHeight = 20.sp
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(Locals.spacing.s)
        ) {
            InfoRow(
                icon = PhosphorIcons.Regular.Clock,
                text = time,
                iconColor = Locals.extras.primary,
            )
            InfoRow(
                icon = PhosphorIcons.Regular.MapPin,
                text = location,
                iconColor = Locals.extras.primary,
            )
            InfoRow(
                icon = PhosphorIcons.Regular.Users,
                text = participants,
                iconColor = Locals.extras.primary,
            )

        }

    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    text: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.l)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(Locals.icons.smallPlus),
            tint = iconColor
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                color = Locals.extras.foreground
            )
        )
    }
}