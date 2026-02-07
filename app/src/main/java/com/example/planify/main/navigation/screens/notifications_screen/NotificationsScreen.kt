package com.example.planify.main.navigation.screens.notifications_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.navigation.screens.notifications_screen.components.NotificationCard
import com.example.planify.main.navigation.screens.notifications_screen.components.NotificationScreenTopBar
import com.example.planify.main.navigation.screens.notifications_screen.components.NotificationType
import java.time.LocalDateTime

val meet = Meeting(
    id = 123L,
    ownerId = 15L,
    name = "yaica",
    description = "yaica123",
    location = "yaica12",
    startsAt = LocalDateTime.now(),
    duration = 1
)

@Composable
fun NotificationsScreen(
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme

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
                        end = Locals.spacing.m
                    )
                    .fillMaxSize()
                    .background(
                        color = colors.background
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.s)
            ) {
                repeat(3) {
                    NotificationCard(
                        type = NotificationType.INVITE_ACCEPTED,
                        firstName = "Oleg",
                        lastName = "Mongol",
                        meeting = meet
                    )

                    NotificationCard(
                        type = NotificationType.INVITE_INCOMING,
                        firstName = "Oleg",
                        lastName = "Mongol",
                        meeting = meet
                    )

                    NotificationCard(
                        type = NotificationType.MEETING_UPDATED,
                        firstName = "Oleg",
                        lastName = "Mongol",
                        meeting = meet
                    )

                    NotificationCard(
                        type = NotificationType.RESCHEDULED_MEETING,
                        firstName = "Oleg",
                        lastName = "Mongol",
                        meeting = meet
                    )
                }
            }
        }

    }

}