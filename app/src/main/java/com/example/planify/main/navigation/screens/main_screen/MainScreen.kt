package com.example.planify.main.navigation.screens.main_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.auth.domain.services_impl.UsersServiceImplST
import com.example.planify.main.features.meetings.meeting.create_meeting.CreateMeetingDialog
import com.example.planify.main.features.meetings.meeting.domain.services_impl.MeetingServiceImplST
import com.example.planify.main.features.profile.domain.services_impl.ProfilesServiceImplST
import com.example.planify.main.navigation.screens.main_screen.components.BottomNavBar
import com.example.planify.main.navigation.screens.main_screen.components.TopBar
import com.example.planify.main.navigation.screens.main_screen.views.home.home_view.ui.HomeView
import com.example.planify.main.navigation.screens.main_screen.views.profile.ProfileView

@Composable
fun MainScreen(
    onSettings: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    var opened by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainScreen(
            onSettings = onSettings,
            onOpen = { opened = true }
        )

        AnimatedVisibility(
            visible = opened,
            exit = fadeOut(),
            enter = fadeIn(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { opened = false }
            )
        }
        AnimatedVisibility(
            visible = opened,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.42f)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                tonalElevation = 6.dp,
                shadowElevation = 12.dp,
                shape = MaterialTheme.shapes.extraLarge,
                color = colors.background
            ) {
                CreateMeetingDialog(
                    onClose = { opened = false }
                )
            }
        }
    }
}

@Composable
private fun MainScreen(
    onSettings: () -> Unit,
    onOpen: () -> Unit
) {
    val router = rememberPagerRouterScreenState(
        routes = MainScreenRoute.routes,
        startRoute = MainScreenRoute.Home
    )
    val colors = MaterialTheme.colorScheme

    var monthTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(
            pagerRouter = router,
            monthTitle = monthTitle
            ) },
        bottomBar = { BottomNavBar(
            pagerRouter = router,
            onOpenCreateDialog = onOpen
        ) },
        containerColor = colors.background
    ) { padding ->
        PagerRouterScreen(
            modifier = Modifier.fillMaxSize(),
            state = router
        ) {
            screen(MainScreenRoute.Home) {
                HomeView(
                    scaffoldPadding = padding,
                    setMonthTitle = { monthTitle = it },
                    meetingService = MeetingServiceImplST.get()
                )
            }
            screen(MainScreenRoute.Chat) {Screen()}
            screen(MainScreenRoute.Inbox) {Screen()}
            screen(MainScreenRoute.Profile) {
                ProfileView(
                    scaffoldPadding = padding,
                    profileService = ProfilesServiceImplST.get(),
                    usersService = UsersServiceImplST.get(),
                    onSettings = onSettings
                )
            }
        }
    }
}

@Composable
fun Screen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("123123123")
    }
}