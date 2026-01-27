package com.example.planify.main.navigation.screens.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planify.R
import com.example.planify.core.ui.pager_router_screen.PagerRouterNavigator
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.themes.icons.LocalIcons
import com.example.planify.main.common.themes.shapes.LocalShapes
import com.example.planify.main.common.ui.TextOnSurface
import com.example.planify.main.common.ui.objectClickable
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.features.create_meet_dialog.ui.CreateMeetDialogView
import com.example.planify.main.navigation.screens.main_screen.MainScreenRoute

@Composable
private fun BottomNavItem(
    title: String,
    icon: Painter,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = Locals.shapes.mediumShape)
                .objectClickable(
                    onClick = onClick
                )
        ) {
            Box(
                modifier = Modifier
                    .withShapeBackground(
                        color = if (selected) colors.primary else Color.Transparent,
                        shape = Locals.shapes.mediumShape
                    )
                    .padding(Locals.spacing.s)
            ) {
                Icon(
                    modifier = Modifier.size(Locals.icons.small),
                    painter = icon,
                    contentDescription = null,
                    tint = if (selected) colors.onPrimary else colors.onSurface
                )
            }
        }

        TextOnSurface(text = title)
    }
}

@Composable
fun FloatingActionItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = Locals.shapes.largeShape)
                .objectClickable(
                    onClick = onClick
                )
        ) {
            Box(
                modifier = Modifier
                    .withShapeBackground(
                        color = colors.onPrimaryContainer,
                        shape = Locals.shapes.largeShape
                    )
            ) {
                Icon(
                    modifier = Modifier.size(Locals.icons.large),
                    painter = icon,
                    contentDescription = null,
                    tint = colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(
    pagerRouter: PagerRouterNavigator
) {
    var showDialog by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.bottomBarHeight)
            .padding(horizontal = Locals.spacing.s),
        color = colors.primaryContainer,
        shape = Locals.shapes.bottomNavBarShape
    ) {
       Row(
           modifier = Modifier,
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceAround
       ) {

           BottomNavItem(
               title = stringResource(R.string.home),
               icon = painterResource(R.drawable.home),
               selected = pagerRouter.currentRoute == MainScreenRoute.Home,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Home)
               }
           )

           BottomNavItem(
               title = stringResource(R.string.chats),
               icon = painterResource(R.drawable.message),
               selected = pagerRouter.currentRoute == MainScreenRoute.Chat,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Chat)
               }
           )

           Spacer(modifier = Modifier.width(Locals.spacing.xxl))

           BottomNavItem(
               title = stringResource(R.string.inbox),
               icon = painterResource(R.drawable.tray_full),
               selected = pagerRouter.currentRoute == MainScreenRoute.Inbox,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Inbox)
               }
           )

           BottomNavItem(
               title = stringResource(R.string.profile),
               icon = painterResource(R.drawable.account),
               selected = pagerRouter.currentRoute == MainScreenRoute.Profile,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Profile)
               }
           )
       }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FloatingActionItem(
            modifier = Modifier
                .offset(y = (-24).dp)
                .align(Alignment.Center),
            icon = painterResource(R.drawable.plus),
            onClick = { showDialog = true }
        )
    }

    if(showDialog) {
        CreateMeetDialogView(
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false }
        )
    }
}