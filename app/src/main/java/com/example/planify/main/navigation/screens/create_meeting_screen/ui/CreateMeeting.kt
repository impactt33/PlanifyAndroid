package com.example.planify.main.navigation.screens.create_meeting_screen.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.planify.core.ui.pager_router_screen.PagerRouterScreen
import com.example.planify.core.ui.pager_router_screen.rememberPagerRouterScreenState
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.meetings.domain.entities.Participant
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.navigation.screens.create_meeting_screen.CreateMeetingRoute
import com.example.planify.main.navigation.screens.create_meeting_screen.CreateMeetingViewModel
import com.example.planify.main.navigation.screens.create_meeting_screen.ui.steps.CreateMeetingStep1
import com.example.planify.main.navigation.screens.create_meeting_screen.ui.steps.CreateMeetingStep2
import com.example.planify.main.navigation.screens.create_meeting_screen.ui.steps.CreateMeetingStep3
import com.example.planify.main.navigation.screens.create_meeting_screen.components.BottomBar
import com.example.planify.main.navigation.screens.create_meeting_screen.components.TopBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun CreateMeeting(
    onBack: () -> Unit,
    navController: NavController
) {
    CreateMeeting(
        viewModel = hiltViewModel(),
        navController = navController,
        onBack = onBack
    )
}

@Composable
private fun CreateMeeting(
    viewModel: CreateMeetingViewModel,
    navController: NavController,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val draft by viewModel.draft.collectAsState()
    val search by viewModel.profilesSearch.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { route ->
            navController.navigate(route.route)
        }
    }

    val router = rememberPagerRouterScreenState(
        routes = CreateMeetingRoute.routes,
        startRoute = CreateMeetingRoute.Info
    )

    val colors = MaterialTheme.colorScheme

    var selectedId by remember { mutableStateOf("00") }

    var selectedParticipants by remember { mutableStateOf(setOf<Participant>()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = colors.background,
        bottomBar = {
            BottomBar(
                currentPage = router.currentRouteIndex,
                onBackButton = { router.navigateTo(router.currentRouteIndex - 1) },
                onButtonClick = { router.navigateTo(router.currentRouteIndex + 1) },
                onCreate = {
                    viewModel.createMeeting(
                        invitedUsersIds = listOf(1L, 2L),
                        schema = createMeetingSchema
                    )
                }
            )
        },
        topBar = {
            TopBar(
                currentPage = router.currentRouteIndex,
                onBack = onBack
            )
        }
    ) { padding ->
        PagerRouterScreen(
            modifier = Modifier
                .padding(padding)
                .wrapContentHeight()
                .animateContentSize(),
            userScrollEnabled = false,
            state = router
        ) {
            screen(CreateMeetingRoute.Info) {
                CreateMeetingStep1(
                    draft = draft,
                    setName = { viewModel.setName(it) },
                    setDesc = { viewModel.setDescription(it) },
                    setLocation = { viewModel.setLocation(it) },
                    setDate = { viewModel.setStartsAtDate(it) },
                )
            }
            screen(CreateMeetingRoute.Time) {
                CreateMeetingStep2(
                    setTime = { viewModel.setStartsAtTime(it) },
                    onSelected = { selectedId = it.id },
                    selectedId = selectedId
                )
            }
            screen(CreateMeetingRoute.Participants) {
                CreateMeetingStep3(
                    search = search,
                    invitedIds = draft.invitedUsersIds,
                    onQueryChange = viewModel::setProfilesQuery,
                    onToggleInvite = viewModel::toggleInvite,
                    onRefresh = viewModel::refreshProfiles,
                    onLoadNext = viewModel::loadNextProfiles
                )
            }
        }
    }
}