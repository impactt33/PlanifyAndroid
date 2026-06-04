package com.example.planify.main.navigation.screens.notifications_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.navigation.screens.notifications_screen.components.NotificationCard
import com.example.planify.main.navigation.screens.notifications_screen.components.NotificationScreenTopBar
import com.example.planify.main.navigation.screens.notifications_screen.components.NotificationType
@Composable
fun NotificationsScreen(
    viewModel: NotificationScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = colors.background,
        topBar = {
            NotificationScreenTopBar(
                height = Locals.dimens.topBarHeight,
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding() + Locals.spacing.s,
                        start = Locals.spacing.m,
                        end = Locals.spacing.m,
                        bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .background(
                        color = colors.background
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.s)
            ) {
                uiState.actions.values.reversed().forEach { action ->
                    when(action) {
                        is ResourceState.Error -> {}
                        is ResourceState.Idle -> {}
                        is ResourceState.Loading -> {}
                        is ResourceState.Refreshing -> {}
                        is ResourceState.Success -> {
                            when(action.data) {
                                is NotificationAction.NotificationInvite -> {
                                    NotificationCard(
                                        type = NotificationType.INVITE_INCOMING,
                                        firstName = action.data.senderProfile.firstName,
                                        lastName = action.data.senderProfile.lastName,
                                        meeting = action.data.meetingContext.meeting
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}