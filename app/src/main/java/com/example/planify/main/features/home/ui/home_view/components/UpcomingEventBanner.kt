package com.example.planify.main.features.home.ui.home_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.UpcomingEventText

@Composable
fun UpcomingEventBanner(
    time: String,
    event: String
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.upcomingEventBannerHeight)
            .padding(Locals.spacing.m)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8F)
                .fillMaxHeight()
                .clip(Locals.shapes.mediumShape)
                .background(colors.primaryContainer)
        ) {
            UpcomingEventText(
                modifier = Modifier
                    .padding(Locals.spacing.m),
                time = time,
                event = event
            )
        }
    }
}