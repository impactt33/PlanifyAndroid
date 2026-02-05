package com.example.planify.main.features.meetings.ui_screens

import android.support.v4.app.INotificationSideChannel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CalendarBlank
import com.adamglin.phosphoricons.regular.Clock
import com.adamglin.phosphoricons.regular.Person
import com.adamglin.phosphoricons.regular.Placeholder
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.features.auth.domain.entities.UserPrivate
import com.example.planify.main.features.create_meeting.entities.Participant
import com.example.planify.main.features.meetings.domain.entities.Meeting
import com.example.planify.main.features.meetings.domain.entities.MeetingContext
import com.example.planify.main.features.meetings.domain.entities.MeetingInvite
import com.example.planify.main.features.meetings.domain.entities.MeetingInviteStatus
import com.example.planify.main.features.meetings.ui_screens.components.TopBar
import com.example.planify.main.features.profiles.domain.entities.Profile
import org.jetbrains.annotations.Async


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

val fakeMeetingContext = MeetingContext(
    participantProfiles = listOf(
        Profile(
            userId = 11L,
            firstName = "Олег",
            lastName = "Смирнов",
            position = "Тимлид",
            department = "IT",
            profileImageUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=800&q=80"
        ),
        Profile(
            userId = 12L,
            firstName = "Тимофей",
            lastName = "Голицын",
            position = "Android Developer",
            department = "IT",
            profileImageUrl = "https://images.unsplash.com/photo-1527980965255-d3b416303d12?auto=format&fit=crop&w=800&q=80"
        ),
        Profile(
            userId = 13L,
            firstName = "Камилла",
            lastName = "Ахметова",
            position = "Product Manager",
            department = "Продукт",
            profileImageUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=800&q=80"
        ),
        Profile(
            userId = 14L,
            firstName = "Дмитрий",
            lastName = "Козлов",
            position = "Backend Developer",
            department = "IT",
            profileImageUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?auto=format&fit=crop&w=800&q=80"
        )
    ),
    invites = listOf(
        MeetingInvite(
            uuid = "e24f2c1e-2a1a-4f61-9b7b-3b3e2b0f7f02",
            meetingId = 1001L,
            senderId = 11L,
            targetUserId = 12L,
            status = MeetingInviteStatus.ACCEPTED,
            createdAt = LocalDateTime.of(2026, 1, 18, 12, 11, 0),
            updatedAt = LocalDateTime.of(2026, 1, 18, 12, 40, 0)
        ),
        MeetingInvite(
            uuid = "a9b1c4d8-0d3f-4c2b-9b54-9b7e6a3a1c9f",
            meetingId = 1001L,
            senderId = 11L,
            targetUserId = 13L,
            status = MeetingInviteStatus.PENDING,
            createdAt = LocalDateTime.of(2026, 1, 18, 12, 12, 0),
            updatedAt = LocalDateTime.of(2026, 1, 18, 12, 12, 0)
        ),
        MeetingInvite(
            uuid = "c0f3f88c-2f5a-4b5b-9c3d-0bbd4aa1c2de",
            meetingId = 1001L,
            senderId = 11L,
            targetUserId = 14L,
            status = MeetingInviteStatus.ACCEPTED,
            createdAt = LocalDateTime.of(2026, 1, 18, 12, 13, 0),
            updatedAt = LocalDateTime.of(2026, 1, 18, 13, 5, 0)
        )
    ),
    meeting = Meeting(
        id = 1001L,
        ownerId = 11L,
        name = "Daily Sync",
        description = "Ежедневная синхронизация команды разработки",
        location = "Конференц-зал A / Zoom",
        startsAt = LocalDateTime.of(2026, 1, 22, 9, 0, 0),
        duration = 60
    )
)


@Composable
fun MeetingInfoScreen(
    meetingInfo: MeetingContext = fakeMeetingContext,
    onBack: () -> Unit
) {

    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    val formatter1 = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))
    val formatter2 = DateTimeFormatter.ofPattern("HH:mm", Locale("ru"))

    Scaffold(
        topBar = { TopBar(
            onBack = { }
        ) },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = colors.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = meetingInfo.meeting.name,
                style = MaterialTheme.typography.displayMedium,
                color = colors.onBackground
            )
            Text(
                text = meetingInfo.meeting.description,
                style = MaterialTheme.typography.headlineMedium,
                color = colors.onBackground
            )
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
                    desc = "${meetingInfo.meeting.startsAt.format(formatter1)}, ${meetingInfo.meeting.startsAt.dayOfWeek}"
                )
                InfoMeetingRow(
                    modifier = Modifier.padding(Locals.spacing.m),
                    icon = PhosphorIcons.Regular.Clock,
                    title = stringResource(R.string.time),
                    desc = "${meetingInfo.meeting.startsAt.format(formatter2)} - ${meetingInfo.meeting.startsAt.plusHours(meetingInfo.meeting.duration.toLong()).format(formatter2)}"
                )
                InfoMeetingRow(
                    modifier = Modifier.padding(Locals.spacing.m),
                    icon = PhosphorIcons.Regular.Placeholder,
                    title = stringResource(R.string.place),
                    desc = meetingInfo.meeting.location
                )
                InfoMeetingRow(
                    modifier = Modifier.padding(Locals.spacing.m),
                    icon = PhosphorIcons.Regular.Person,
                    title = stringResource(R.string.owner),
                    desc = meetingInfo.participantProfiles
                        .first { it.userId == meetingInfo.meeting.ownerId }
                        .let { "${it.firstName} ${it.lastName}" }
                )
            }
            Spacer(modifier = Modifier.height(Locals.spacing.l))
            Text(
                text = "${stringResource(R.string.participants)} (${meetingInfo.participantProfiles.size})",
                style = MaterialTheme.typography.labelLarge,
                color = colors.onBackground
            )
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(meetingInfo.participantProfiles) { participant ->
                        InfoParticipantRow(
                            modifier = Modifier.padding(Locals.spacing.m),
                            profileUrl = participant.profileImageUrl,
                            title = "${participant.firstName} ${participant.lastName}",
                            desc = participant.position,
                            isAccepted = meetingInfo.invites.firstOrNull {
                                it.targetUserId == participant.userId
                            } ?.let { true } ?: false
                        )
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.xs)
    ) {
        Box(
            modifier = Modifier
                .size(Locals.icons.mediumPlus)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = profileUrl,
                contentDescription = null
            )
        }
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
                color = Locals.extras.muted
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
                text = if (isAccepted) stringResource(R.string.accepted)
                    else stringResource(R.string.not_accepted),
                style = MaterialTheme.typography.bodySmall,
                color = if (isAccepted) Color.Green
                    else colors.onErrorContainer
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
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.xs)
    ) {
        Icon(
            modifier = Modifier
                .size(Locals.icons.medium),
            imageVector = icon,
            contentDescription = null
        )
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
                color = Locals.extras.muted
            )
        }
    }
}