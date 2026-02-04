package com.example.planify.main.navigation.screens.create_meeting_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CaretLeft
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground

@Composable
fun BottomBar(
    currentPage: Int,
    onBackButton: () -> Unit,
    onButtonClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.createMeetingBottomBarHeight)
            .background(colors.surface),
        verticalArrangement = Arrangement.spacedBy(Locals.spacing.xxs),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Locals.spacing.m,
                        vertical = Locals.spacing.s),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${currentPage+1}/3",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurface
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = when(currentPage) {
                        0 -> { stringResource(R.string.title_info) }
                        1 -> { stringResource(R.string.title_time) }
                        2 -> { stringResource(R.string.title_participants) }
                        else -> { "" }
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurface
                )
            }

            when(currentPage) {
                0 -> {
                    BottomButton(
                        text = "Далее",
                        onClick = onButtonClick
                    )
                }
                1 -> {
                    RowWith2Buttons(
                        mainButtonTitle = "Далее",
                        onBackButton = onBackButton,
                        onButtonClick = onButtonClick
                    )
                }
                2 -> {
                    RowWith2Buttons(
                        mainButtonTitle = "Создать",
                        onBackButton = onBackButton,
                        onButtonClick = onButtonClick
                    )
                }
            }
        }

    }
}

@Composable
fun RowWith2Buttons(
    mainButtonTitle: String,
    onBackButton: () -> Unit,
    onButtonClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                paddingValues = PaddingValues(
                    start = Locals.spacing.m
                )
            )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .withShapeBackground(
                    shape = CircleShape,
                    color = colors.surface
                )
                .clickable(
                    onClick = onBackButton
                )
        ) {
            Icon(
                modifier = Modifier
                    .size(Locals.icons.medium)
                    .padding(Locals.spacing.xs),
                imageVector = PhosphorIcons.Regular.CaretLeft,
                contentDescription = null
            )
        }

        BottomButton(
            text = mainButtonTitle,
            onClick = onButtonClick
        )
    }
}

@Composable
fun BottomButton(
    text: String,
    onClick: () -> Unit
) {
    val shape = Locals.shapes.mediumShape
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Locals.spacing.m
            )
            .height(Locals.dimens.createMeetingBottomButtonHeight),
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Locals.gradients.blue,
                    shape = shape
                )
                .clickable(
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(Locals.spacing.m),
                text = text,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colors.onPrimary
            )
        }
    }
}