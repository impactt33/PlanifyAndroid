package com.example.planify.main.navigation.screens.inbox_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planify.R
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.navigation.screens.inbox_screen.InboxRoute

@Composable
fun InboxView(
    scaffoldPadding: PaddingValues
) {
    val pagerState = rememberPagerRouterScreenState(
        routes = InboxRoute.routes,
        startRoute = InboxRoute.Incoming
    )

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Locals.dimens.inboxBoxTopNavBarHeight)
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = Locals.spacing.m,
                        vertical = Locals.spacing.s
                    )
                    .withShapeBackground(
                        color = colors.surface,
                        shape = Locals.shapes.mediumShape
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Locals.spacing.m)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(Locals.spacing.xxs)
                            .withShapeBackground(
                                color = colors.primaryContainer.copy(alpha = 0.2f),
                                shape = Locals.shapes.mediumShape
                            )
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                           text = stringResource(R.string.incoming),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.primary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(Locals.spacing.xxs)
                            .withShapeBackground(
                                color = colors.primaryContainer.copy(alpha = 0.2f),
                                shape = Locals.shapes.mediumShape
                            )
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.sent),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.primary
                        )
                    }
                }
            }

            PagerRouterScreen(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {
                screen()
            }
        }
    }
}