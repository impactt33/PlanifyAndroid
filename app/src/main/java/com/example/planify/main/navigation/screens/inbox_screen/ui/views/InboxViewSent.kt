package com.example.planify.main.navigation.screens.inbox_screen.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.navigation.AppRoute
import com.example.planify.main.navigation.screens.inbox_screen.InboxViewModel
import com.example.planify.main.navigation.screens.inbox_screen.ui.components.MeetingInviteInboxCardShort

@Composable
fun InboxViewSent(
    viewModel: InboxViewModel,
    navController: NavController
) {
    val meetings by viewModel.meetings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Locals.dimens.inboxBoxTopNavBarHeight,
                    bottom = Locals.spacing.s
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.m)
            ) {
                meetings.forEach {
                    MeetingInviteInboxCardShort(
                        meeting = it.meeting,
                        participantProfiles = it.participantProfiles,
                        invites = it.invites,
                        onClick = { meetingId ->
                            navController.navigate(AppRoute.MeetingInfoMenu(meetingId).route)
                        }
                    )
                }
            }
        }
    }
}