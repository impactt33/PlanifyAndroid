package com.example.planify.main.navigation.screens.create_meeting_screen.ui.steps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.auth.domain.entities.UserPrivate
import com.example.planify.main.features.meetings.domain.entities.Participant
import com.example.planify.main.features.profiles.domain.entities.Profile

private val participants123 = listOf(
    Participant(
        user = UserPrivate(
            id = 1L,
            email = "pidrila@mail.ru",
            username = "sdfsdf"
        ),
        profile = Profile(
            firstName = "Олег",
            lastName = "Тиньков",
            position = "Иноагент",
            department = "Италия",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 2L,
            email = "kamilla.akhmetova@planify.ru",
            username = "kamilla_pm"
        ),
        profile = Profile(
            firstName = "Камилла",
            lastName = "Ахметова",
            position = "Product Manager",
            department = "Продукт",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 3L,
            email = "andrey.petrov@planify.ru",
            username = "andrey_design"
        ),
        profile = Profile(
            firstName = "Андрей",
            lastName = "Петров",
            position = "Дизайнер",
            department = "Дизайн",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 4L,
            email = "elena.ivanova@planify.ru",
            username = "elena_hr"
        ),
        profile = Profile(
            firstName = "Елена",
            lastName = "Иванова",
            position = "HR Manager",
            department = "HR",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 5L,
            email = "dmitry.kozlov@planify.ru",
            username = "dmitry_dev"
        ),
        profile = Profile(
            firstName = "Дмитрий",
            lastName = "Козлов",
            position = "Android Developer",
            department = "IT",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 6L,
            email = "natalia.sokolova@planify.ru",
            username = "natalia_qa"
        ),
        profile = Profile(
            firstName = "Наталья",
            lastName = "Соколова",
            position = "QA Engineer",
            department = "IT",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 7L,
            email = "maxim.volkov@planify.ru",
            username = "max_volkov"
        ),
        profile = Profile(
            firstName = "Максим",
            lastName = "Волков",
            position = "Backend Developer",
            department = "IT",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 8L,
            email = "alisa.fedorova@planify.ru",
            username = "alisa_analytics"
        ),
        profile = Profile(
            firstName = "Алиса",
            lastName = "Фёдорова",
            position = "Data Analyst",
            department = "Аналитика",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 9L,
            email = "kirill.morozov@planify.ru",
            username = "kirill_ops"
        ),
        profile = Profile(
            firstName = "Кирилл",
            lastName = "Морозов",
            position = "DevOps Engineer",
            department = "Инфраструктура",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 10L,
            email = "sofia.orlova@planify.ru",
            username = "sofia_front"
        ),
        profile = Profile(
            firstName = "София",
            lastName = "Орлова",
            position = "Frontend Developer",
            department = "Веб",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 11L,
            email = "ivan.kuznetsov@planify.ru",
            username = "ivan_sales"
        ),
        profile = Profile(
            firstName = "Иван",
            lastName = "Кузнецов",
            position = "Account Manager",
            department = "Продажи",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    ),
    Participant(
        user = UserPrivate(
            id = 12L,
            email = "polina.belova@planify.ru",
            username = "polina_support"
        ),
        profile = Profile(
            firstName = "Полина",
            lastName = "Белова",
            position = "Support Specialist",
            department = "Поддержка",
            profileImageUrl = "https://preview.redd.it/spider-man-v0-t0qia4bou9qf1.jpg?width=640&crop=smart&auto=webp&s=8bb14c332040b37fd9964b8fb8d5ffc4aefef9ac"
        )
    )
)

@Composable
fun CreateMeetingStep3(
    participants: List<Participant> = participants123,
    onSelectedChanged: (List<Participant>) -> Unit,
    selectedParticipants: Set<Participant>
) {
    val colors = MaterialTheme.colorScheme

    var query by remember { mutableStateOf("") }

    var selectedParticipants by remember { mutableStateOf(setOf<Participant>()) } // убрать потом

    val filtered = remember(query, participants) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) participants
        else participants.filter {
            val fullName = it.profile.firstName + it.profile.lastName
            fullName.lowercase().contains(q)
        }
    }

    val selected = remember(selectedParticipants, participants) {
        participants.filter { it in selectedParticipants  }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                PaddingValues(
                    top = Locals.spacing.m,
                    start = Locals.spacing.m,
                    end = Locals.spacing.m,
                )
            )
    ) {
        Text(
            text = stringResource(R.string.step3_inviting),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground
        )

       Spacer(modifier = Modifier.height(Locals.spacing.s))

        Text(
            text = stringResource(R.string.step3_chosen) + " ${selected.size}",
            style = MaterialTheme.typography.bodySmall,
            color = Locals.extras.mutedForeground.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(Locals.spacing.s))

        ChipRow(
            participants = selectedParticipants.toList(),
            onRemove = {
                selectedParticipants = selectedParticipants - it
                onSelectedChanged(participants.filter { it in participants })
            }
        )

        Spacer(modifier = Modifier.height(Locals.spacing.s))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            singleLine = true,
            placeholder = { Text(stringResource(R.string.step3_search_placeholder)) },
            shape = Locals.shapes.mediumShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Locals.extras.border,
                unfocusedBorderColor = Locals.extras.border,
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Locals.spacing.s))

        Divider(color = Locals.extras.border)

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(Locals.spacing.s)
        ) {
            item {
                Spacer(modifier = Modifier.height(0.dp))
            }
            items(filtered, key = { it.user.id }) { participant ->
                val checked = participant in selectedParticipants
                ParticipantRow(
                    participant = participant,
                    checked = checked
                ) {
                    selectedParticipants = if (checked) selectedParticipants - participant
                        else selectedParticipants + participant
                    onSelectedChanged(participants.filter { it in selectedParticipants })
                }
            }
            item {
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}

@Composable
fun ParticipantRow(
    participant: Participant,
    checked: Boolean,
    onToggle: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    val containerColor = if (checked)
        colors.surfaceVariant.copy(alpha = 0.45f)
    else
        colors.surface

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.createMeetingParticipantCardHeight)
            .clip(shape)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onToggle() },
        color = containerColor,
        shape = shape,
        border = BorderStroke(1.dp, Locals.extras.border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Locals.spacing.s),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { onToggle() }
            )

            Spacer(modifier = Modifier.width(Locals.spacing.xs))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(Locals.icons.medium)
                    .background(colors.surfaceVariant.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = participant.profile.profileImageUrl,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(Locals.spacing.s))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${participant.profile.firstName} ${participant.profile.lastName}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (checked) FontWeight.SemiBold else FontWeight.Medium,
                    color = colors.onBackground
                )
                Text(
                    text = participant.profile.department,
                    style = MaterialTheme.typography.bodySmall,
                    color = Locals.extras.mutedForeground.copy(alpha = 0.75f)
                )
            }
        }
    }
}

@Composable
private fun Chip(
    text: String,
    onRemove: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier
            .padding(vertical = Locals.spacing.xxxs),
        shape = Locals.shapes.mediumShape,
        color = colors.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(
            color = Locals.extras.border.copy(alpha = 0.5f),
            width = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Locals.spacing.xs,
                    vertical = Locals.spacing.xxs
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(Locals.spacing.xs))
            Text(
                text = "×",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 18.sp,
                color = Locals.extras.mutedForeground,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onRemove() }
                    .padding(horizontal = Locals.spacing.xxs)
            )
        }
    }
}

@Composable
fun ChipRow(
    participants: List<Participant>,
    onRemove: (Participant) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Locals.spacing.xs)
    ) {
        items(participants) { participant ->
            Chip(
                text = "${participant.profile.firstName} ${participant.profile.lastName}",
                onRemove = { onRemove(participant) }
            )
        }
    }
}