package com.example.planify.main.navigation.screens.meeting_info_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planify.main.common.themes.Locals

@Composable
fun MeetingInfoCard(
    title: String,
    dateTime: String
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.background
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Locals.extras.border
        )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 12.dp
            )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = dateTime,
                style = MaterialTheme.typography.bodySmall,
                color = colors.onBackground
            )
        }
    }
}