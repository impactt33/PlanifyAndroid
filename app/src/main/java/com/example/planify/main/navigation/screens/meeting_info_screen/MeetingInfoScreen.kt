package com.example.planify.main.navigation.screens.meeting_info_screen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CalendarBlank
import com.adamglin.phosphoricons.regular.Clock
import com.adamglin.phosphoricons.regular.MapPin
import com.adamglin.phosphoricons.regular.User
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.meetings.domain.entities.MeetingInviteStatus
import com.example.planify.main.navigation.screens.fixed_screens.ErrorScreen
import com.example.planify.main.navigation.screens.meeting_info_screen.components.RescheduleDialog
import com.example.planify.main.navigation.screens.meeting_info_screen.components.TopBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun MeetingInfoScreen(
    onBack: () -> Unit
) {
    MeetingInfoScreen(
        viewModel = hiltViewModel(),
        onBack = onBack
    )
}

@Composable
private fun MeetingInfoScreen(
    onBack: () -> Unit,
    viewModel: MeetingInfoViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val screenUIState = uiState) {
        is UIState.ContentData -> {
            MeetingInfo(
                onBack = onBack,
                uiState = screenUIState,
                viewModel = viewModel
            )
        }

        is UIState.Error -> {
            ErrorScreen(
                status = screenUIState.message
            )
        }

        is UIState.Loading, UIState.Refreshing -> {
            CircularProgressIndicator()
        }
    }


}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MeetingInfo(
    onBack: () -> Unit,
    uiState: UIState.ContentData,
    viewModel: MeetingInfoViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is MeetingInfoEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    var showRescheduleDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var startHour by rememberSaveable {
        mutableIntStateOf(0)
    }

    var endHour by rememberSaveable {
        mutableIntStateOf(3)
    }

    val stringInterval = (uiState.meetingContext.meeting.startsAt
            to uiState.meetingContext.meeting.startsAt.plusHours(uiState.meetingContext.meeting.duration.toLong()))
        .toStringInterval()

    RescheduleDialog(
        visible = showRescheduleDialog,
        meetingTitle = uiState.meetingContext.meeting.name,
        oldDateTime = stringInterval,
        selectedDate = selectedDate,
        startHour = startHour,
        endHour = endHour,
        onDateSelected = {
            selectedDate = it
        },
        onStartHourSelected = {
            startHour = it
        },
        onEndHourSelected = {
            endHour = it
        },
        onMoveClick = {
            viewModel.rescheduleThisMeeting(selectedDate.atTime(startHour, 0), endHour - startHour)
            showRescheduleDialog = false
        },
        onDismiss = {
            showRescheduleDialog = false
        }
    )

    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    val formatter1 = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))
    val formatter2 = DateTimeFormatter.ofPattern("HH:mm", Locale("ru"))

    val pullRefreshState = rememberPullToRefreshState()

    val authState by viewModel.authFlow.collectAsState()

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                onBack = onBack
            )
        },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = colors.background
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = uiState is UIState.Refreshing,
            onRefresh = {
                if (uiState is UIState.ContentData) {
                    viewModel.runFetchMeetingContext((uiState as UIState.ContentData).meetingContext.meeting.id, refresh = true)
                }
            },
            state = pullRefreshState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(
                        state = rememberScrollState()
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            PaddingValues(
                                top = padding.calculateTopPadding() + Locals.spacing.s,
                                bottom = padding.calculateBottomPadding(),
                                start = Locals.spacing.m,
                                end = Locals.spacing.m
                            )
                        )
                ) {
                    when (val screenUiState = uiState) {
                        is UIState.Loading, is UIState.Refreshing -> {}
                        is UIState.Error -> ErrorScreen((uiState as UIState.Error).message)
                        is UIState.ContentData -> {
                            val meetingInfo = screenUiState.meetingContext

                            Text(
                                text = meetingInfo.meeting.name,
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = colors.onBackground
                            )

                            Spacer(modifier = Modifier.height(Locals.spacing.xs))

                            Text(
                                text = meetingInfo.meeting.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.onBackground
                            )

                            Spacer(modifier = Modifier.height(Locals.spacing.xs))

                            if ((authState as AuthState.Authenticated).context.user.id == meetingInfo.meeting.ownerId &&
                                meetingInfo.meeting.startsAt.isAfter(LocalDateTime.now())
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(Locals.spacing.xs)
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .weight(0.5f),
                                        shape = Locals.shapes.smallShape,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colors.primaryContainer
                                        ),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = Locals.extras.border
                                        ),
                                        onClick = {}
                                    ) {
                                        Text(
                                            text = "Редактировать",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = colors.onBackground
                                        )
                                    }

                                    Button(
                                        modifier = Modifier
                                            .weight(0.5f),
                                        shape = Locals.shapes.smallShape,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colors.primaryContainer
                                        ),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = Locals.extras.border
                                        ),
                                        onClick = {
                                            showRescheduleDialog = true
                                        }
                                    ) {
                                        Text(
                                            text = "Изменить время",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = colors.onBackground
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(Locals.spacing.xs))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = shape,
                                border = BorderStroke(
                                    color = Locals.extras.border,
                                    width = 1.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = colors.surface
                                )
                            ) {
                                InfoMeetingRow(
                                    modifier = Modifier.padding(Locals.spacing.m),
                                    icon = PhosphorIcons.Regular.CalendarBlank,
                                    title = stringResource(R.string.date),
                                    desc = "${meetingInfo.meeting.startsAt.format(formatter1)}, ${meetingInfo.meeting.startsAt.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))}"
                                )
                                InfoMeetingRow(
                                    modifier = Modifier.padding(Locals.spacing.m),
                                    icon = PhosphorIcons.Regular.Clock,
                                    title = stringResource(R.string.time),
                                    desc = "${meetingInfo.meeting.startsAt.format(formatter2)} - ${meetingInfo.meeting.startsAt.plusHours(meetingInfo.meeting.duration.toLong()).format(formatter2)}"
                                )
                                InfoMeetingRow(
                                    modifier = Modifier.padding(Locals.spacing.m),
                                    icon = PhosphorIcons.Regular.MapPin,
                                    title = stringResource(R.string.place),
                                    desc = meetingInfo.meeting.location
                                )
                                InfoMeetingRow(
                                    modifier = Modifier.padding(Locals.spacing.m),
                                    icon = PhosphorIcons.Regular.User,
                                    title = stringResource(R.string.owner),
                                    desc = meetingInfo.participantProfiles
                                        .first { it.userId == meetingInfo.meeting.ownerId }
                                        .let { "${it.firstName} ${it.lastName}" }
                                )
                            }

                            Spacer(modifier = Modifier.height(Locals.spacing.l))

                            Text(
                                text = "${stringResource(R.string.participants)} (${meetingInfo.participantProfiles.size}/${meetingInfo.invitedUserProfiles.size + 1})",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.onBackground
                            )

                            Spacer(modifier = Modifier.height(Locals.spacing.s))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = shape,
                                border = BorderStroke(
                                    color = Locals.extras.border,
                                    width = 1.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = colors.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Log.d("participantProfiles", "entered")

                                    val owner = meetingInfo.participantProfiles.first {
                                        meetingInfo.meeting.ownerId == it.userId
                                    }

                                    InfoOwnerParticipantRow(
                                        modifier = Modifier.padding(Locals.spacing.m),
                                        profileUrl = owner.profileImageUrl,
                                        title = "${owner.firstName} ${owner.lastName}",
                                        desc = owner.position
                                    )

                                    meetingInfo.invitedUserProfiles.forEach { participant ->
                                        Log.d("participantProfiles", "participant ${participant.userId}")
                                        InfoParticipantRow(
                                            modifier = Modifier.padding(Locals.spacing.m),
                                            profileUrl = participant.profileImageUrl,
                                            title = "${participant.firstName} ${participant.lastName}",
                                            desc = participant.position,
                                            isAccepted = meetingInfo.invites.firstOrNull {
                                                it.targetId == participant.userId && it.status == MeetingInviteStatus.ACCEPTED
                                            }?.let { true } ?: false
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
}

@Composable
fun InfoParticipantRow(
    modifier: Modifier = Modifier,
    profileUrl: String,
    title: String,
    desc: String,
    isAccepted: Boolean
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Locals.icons.mediumLower)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = profileUrl,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(Locals.spacing.m))

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Locals.spacing.xxs),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = colors.onBackground
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = Locals.extras.mutedForeground
            )
        }

        Box(
            modifier = Modifier
                .withShapeBackground(
                    color = if (isAccepted) Color.Green.copy(alpha = 0.2f)
                    else colors.errorContainer,
                    shape = Locals.shapes.mediumShape
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(Locals.spacing.xxs),
                text = if (isAccepted) stringResource(R.string.accepted)
                else stringResource(R.string.not_accepted),
                style = MaterialTheme.typography.bodySmall,
                color = if (isAccepted) Color.Green.copy(alpha = 0.5f)
                else colors.error
            )
        }
    }
}

@Composable
fun InfoOwnerParticipantRow(
    modifier: Modifier = Modifier,
    profileUrl: String,
    title: String,
    desc: String
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Locals.icons.mediumLower)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = profileUrl,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(Locals.spacing.m))

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Locals.spacing.xxs),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = colors.onBackground
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = Locals.extras.mutedForeground
            )
        }

        Box(
            modifier = Modifier
                .withShapeBackground(
                    color = colors.primaryContainer,
                    shape = Locals.shapes.mediumShape
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(Locals.spacing.xxs),
                text = stringResource(R.string.owner),
                style = MaterialTheme.typography.bodySmall,
                color = colors.primary
            )
        }
    }
}

@Composable
fun InfoMeetingRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    desc: String
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(Locals.icons.smallPlus),
            imageVector = icon,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(Locals.spacing.m))

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(Locals.spacing.xxs)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = colors.onBackground
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = Locals.extras.mutedForeground
            )
        }
    }
}

fun Pair<LocalDateTime, LocalDateTime>.toStringInterval(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:00")

    return "${this.first.format(formatter)} - ${this.second.format(formatter)}"
}