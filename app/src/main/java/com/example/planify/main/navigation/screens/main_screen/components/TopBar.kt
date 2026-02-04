package com.example.planify.main.navigation.screens.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Bold
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.bold.Bell
import com.adamglin.phosphoricons.regular.CalendarDots
import com.adamglin.phosphoricons.regular.MagnifyingGlass
import com.example.planify.R
import com.example.planify.core.ui.pager_router_screen.PagerRouterNavigator
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.themes.shapes.shapes
import com.example.planify.main.common.ui.PlaceholderText
import com.example.planify.main.common.ui.TopBarTitleText
import com.example.planify.main.common.ui.TopBarTitleTextLarge
import com.example.planify.main.common.ui.TopBarTitleTextSecondary
import com.example.planify.main.common.ui.objectClickable
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.navigation.screens.main_screen.MainScreenRoute

@Composable
fun NotificationIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val gradient = Locals.gradients

    val shape = CircleShape

    Box(
        modifier = Modifier
            .shadow(
                elevation = Locals.dimens.elevation,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .clip(shape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(Locals.icons.medium)
                    .objectClickable(
                        onClick = onClick
                    )
                    .withShapeBackground(
                        gradient = gradient.blue,
                        shape = shape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(Locals.icons.smallPlus),
                    imageVector = PhosphorIcons.Bold.Bell,
                    contentDescription = null,
                    tint = colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun SecondaryHomeInfo(
    month: String
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.xxs)
    ) {
        Icon(
            modifier = Modifier
                .size(Locals.icons.smallPlus),
            imageVector = PhosphorIcons.Regular.CalendarDots,
            contentDescription = null,
            tint = colors.onPrimaryContainer
        )
        TopBarTitleTextSecondary(
            modifier = Modifier,
            text = month
        )
    }
}

@Composable
fun SecondaryInboxInfo(
    countUnread: Int
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.xxs)
    ) {
        TopBarTitleTextSecondary(
            modifier = Modifier,
            text = stringResource(R.string.n_waiting_answer, "$countUnread")
        )
    }
}

@Composable
fun GlassSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String
) {
    val colors = MaterialTheme.colorScheme
    val extras = Locals.extras
    val shape = Locals.shapes.mediumShape

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Locals.dimens.searchBarHeight)
            .shadow(
                elevation = Locals.dimens.elevation,
                shape = shape,
                clip = false,
                spotColor = extras.glass.shadow
            )
            .clip(shape)
            .background(extras.glass.bgStrong)
            .padding(horizontal = Locals.spacing.m),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = PhosphorIcons.Regular.MagnifyingGlass,
                contentDescription = null,
                tint = colors.onPrimaryContainer.copy(alpha = 0.65f),
                modifier = Modifier
                    .size(Locals.icons.smallPlus),
            )

            Spacer(modifier = Modifier.width(Locals.spacing.s))

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {}),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 18.sp,
                        color = colors.onPrimaryContainer
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier,
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                PlaceholderText(
                                    modifier = Modifier,
                                    text = placeholder
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeTopBar(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start
        ) {
            TopBarTitleText(title = title)
            SecondaryHomeInfo(description)
        }

        Spacer(modifier = Modifier.weight(1f))

        NotificationIcon(
            onClick = onClick
        )
    }
}

@Composable
fun ChatTopBar(
    title: String
) {

    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start
        ) {
            TopBarTitleText(title = title)
            GlassSearchBar(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier,
                placeholder = stringResource(R.string.glass_search_placeholder)
            )
        }
    }
}

@Composable
fun InboxTopBar(
    title: String,
    description: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start
        ) {
            TopBarTitleText(title = title)
            SecondaryInboxInfo(description)
        }

        Spacer(modifier = Modifier.weight(1f))

        NotificationIcon(
            onClick = onClick
        )
    }
}

@Composable
fun ProfileTopBar(
    title: String
) {
    Row(
        modifier = Modifier
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            TopBarTitleTextLarge(title = title)
        }
    }
}
@Composable
fun TopBar(
    pagerRouter: PagerRouterNavigator,
    monthTitle: String
) {
    val colors = MaterialTheme.colorScheme

    val countUnread = 4

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.topBarHeight),
        color = colors.surface
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(Locals.spacing.m),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                when(pagerRouter.currentRoute.key) {
                    MainScreenRoute.Home.key -> HomeTopBar(
                        title = stringResource(R.string.schedule),
                        description = monthTitle
                    ) { }
                    MainScreenRoute.Inbox.key -> InboxTopBar(
                        title = stringResource(R.string.inbox),
                        description = countUnread
                    ) { }
                    MainScreenRoute.Chat.key -> ChatTopBar(
                        title = stringResource(R.string.chats)
                    )
                    MainScreenRoute.Profile.key -> ProfileTopBar(
                        title = stringResource(R.string.profile)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            NotificationIcon(
                onClick = {}
            )
        }
    }
}
