package com.example.planify.main.features.meetings.ui_screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CaretLeft
import com.example.planify.R
import com.example.planify.main.common.themes.Locals

@Composable
fun TopBar(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.topBarDetailsHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .clickable { onBack() }
        ) {
            Icon(
                modifier = Modifier
                    .size(Locals.icons.medium),
                imageVector = PhosphorIcons.Regular.CaretLeft,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(Locals.spacing.s))
        Text(
            text = stringResource(R.string.meeting_detail),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}