package com.example.planify.main.navigation.screens.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planify.R
import com.example.planify.core.ui.pager_router_screen.PagerRouterNavigator
import com.example.planify.main.common.themes.dimens.LocalDimens
import com.example.planify.main.common.themes.icons.LocalIcons
import com.example.planify.main.common.themes.padding.LocalPadding
import com.example.planify.main.common.themes.shapes.LocalShapes
import com.example.planify.main.common.themes.shapes.shapes
import com.example.planify.main.common.themes.spacing.LocalSpacing
import com.example.planify.main.common.themes.spacing.Spacing
import com.example.planify.main.common.ui.objectClickable
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.features.create_meet_dialog.ui.CreateMeetDialogView
import com.example.planify.main.navigation.screens.main_screen.MainScreenRoute

@Composable
private fun BottomNavItem(
    icon: Painter,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val shapes = LocalShapes.current
    val icons = LocalIcons.current
    val padding = LocalPadding.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = shapes.smallShape)
                .objectClickable(
                    onClick = onClick
                )
        ) {
            Box(
                modifier = Modifier
                    .withShapeBackground(
                        color = if (selected) Color(0xFF086FA1) else Color.Transparent,
                        shape = shapes.smallShape
                    )
                    .padding(padding.bottomNavBarIconDp)
            ) {
                Icon(
                    modifier = Modifier.size(icons.small),
                    painter = icon,
                    contentDescription = null,
                    tint = if (selected) colors.onPrimary else colors.onSurface
                )
            }
        }
    }
}

@Composable
fun FloatingActionItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit
) {
    val shapes = LocalShapes.current
    val icons = LocalIcons.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = shapes.largeShape)
                .objectClickable(
                    onClick = onClick
                )
        ) {
            Box(
                modifier = Modifier
                    .withShapeBackground(
                        color = Color(0xFF086FA1),
                        shape = shapes.largeShape
                    )
            ) {
                Icon(
                    modifier = Modifier.size(icons.mediumPlus),
                    painter = icon,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(
    pagerRouter: PagerRouterNavigator
) {
    val colors = MaterialTheme.colorScheme
    val dimens = LocalDimens.current
    val padding = LocalPadding.current
    val shapes = LocalShapes.current
    val spacing = LocalSpacing.current

    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimens.bottomBarHeightDp)
            .padding(horizontal = padding.bottomNavBarDp),
        color = Color(0xFF3CA0D0),
        shape = shapes.bottomNavBarShape
    ) {
       Row(
           modifier = Modifier,
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceAround
       ) {

           BottomNavItem(
               icon = painterResource(R.drawable.home),
               selected = pagerRouter.currentRoute == MainScreenRoute.Home,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Home)
               }
           )

           BottomNavItem(
               icon = painterResource(R.drawable.message),
               selected = pagerRouter.currentRoute == MainScreenRoute.Chat,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Chat)
               }
           )

           Spacer(modifier = Modifier.width(spacing.xxlDp))

           BottomNavItem(
               icon = painterResource(R.drawable.tray_full),
               selected = pagerRouter.currentRoute == MainScreenRoute.Inbox,
               onClick = {
                   pagerRouter.navigateTo(MainScreenRoute.Inbox)
               }
           )

           BottomNavItem(
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