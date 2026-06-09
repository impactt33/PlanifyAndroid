package com.example.planify.main.navigation.screens.meeting_info_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals


@Composable
fun DialogActionRow(
    title: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.buttonMeetingInboxCardHeight),
        shape = Locals.shapes.mediumShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Locals.extras.border
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Locals.spacing.s),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Locals.icons.small),
                tint = colors.onBackground
            )

            Spacer(modifier = Modifier.width(Locals.spacing.s))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = colors.primary
            )

            Spacer(modifier = Modifier.width(Locals.spacing.xs))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(Locals.icons.small),
                tint = colors.onBackground
            )
        }
    }
}