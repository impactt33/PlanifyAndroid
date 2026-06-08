package com.example.planify.main.navigation.screens.notifications_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.actions.domain.utils.ActionStreamId
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

    val items = uiState.actions.entries.sortedWith(
        compareByDescending<Map.Entry<String, ResourceState<NotificationAction>>> { ActionStreamId.millis(it.key) }
            .thenByDescending { ActionStreamId.seq(it.key) }
    )

    val hasReadNotifications = items.any { entry ->
        val state = entry.value
        state is ResourceState.Success && state.data is NotificationAction.NotificationStatusUpdate
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.background,
        topBar = {
            NotificationScreenTopBar(
                height = Locals.dimens.topBarHeight,
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.verticalScroll(state = rememberScrollState())
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
                    .background(color = colors.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.s)
            ) {
                if (hasReadNotifications) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { viewModel.clearRead() }) {
                            Text(text = "Очистить")
                        }
                    }
                }

                items.forEach { entry ->
                    key(entry.key) {
                        when (val state = entry.value) {
                            is ResourceState.Success -> when (val data = state.data) {
                                is NotificationAction.NotificationInvite -> {
                                    NotificationCard(
                                        type = NotificationType.INVITE_INCOMING,
                                        firstName = data.senderProfile.firstName,
                                        lastName = data.senderProfile.lastName,
                                        meeting = data.meetingContext.meeting
                                    )
                                }

                                is NotificationAction.NotificationStatusUpdate -> {
                                    DismissibleNotification(
                                        onDismiss = { viewModel.dismiss(entry.key) }
                                    ) {
                                        NotificationCard(
                                            type = NotificationType.INVITE_ACCEPTED,
                                            firstName = data.targetProfile.firstName,
                                            lastName = data.targetProfile.lastName,
                                            meeting = data.meetingContext.meeting
                                        )
                                    }
                                }
                            }

                            is ResourceState.Error,
                            is ResourceState.Idle,
                            is ResourceState.Loading,
                            is ResourceState.Refreshing -> Unit
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DismissibleNotification(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDismiss()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colors.tertiaryContainer,
                        shape = Locals.shapes.mediumShape
                    )
                    .padding(end = Locals.spacing.m),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Прочитано",
                    color = colors.onTertiaryContainer,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        content = {
            content()
        }
    )
}