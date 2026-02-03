package com.example.planify.main.navigation.screens.main_screen.views.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Briefcase
import com.adamglin.phosphoricons.regular.Buildings
import com.adamglin.phosphoricons.regular.EnvelopeSimple
import com.adamglin.phosphoricons.regular.SignOut
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.features.auth.domain.services.UsersService
import com.example.planify.main.features.auth.domain.entities.UserPrivate
import com.example.planify.main.features.profile.domain.services.ProfilesService
import com.example.planify.main.features.profile.entities.Profile
import com.example.planify.main.navigation.screens.init_screen.components.LoadingView

@Composable
fun ProfileView(
    scaffoldPadding: PaddingValues,
    profileService: ProfilesService,
    usersService: UsersService
) {
    val factory = remember { ProfileViewModelFactory(
        profileService = profileService,
        usersService = usersService
    ) }

    ProfileView(
        viewModel = viewModel(factory = factory),
        scaffoldPadding = scaffoldPadding
    )
}

@Composable
private fun ProfileView(
    viewModel: ProfileViewModel,
    scaffoldPadding: PaddingValues
) {
    val colors = MaterialTheme.colorScheme

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOrFetchUserInfo()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = scaffoldPadding),
        color = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Locals.spacing.m)
                .padding(top = Locals.spacing.xs),
            verticalArrangement = Arrangement.Top
        ) {
            when(uiState) {
                is UIState.Loading -> {
                    LoadingView()
                }

                is UIState.ContentData -> {
                    InfoView(
                        (uiState as UIState.ContentData).profile,
                        (uiState as UIState.ContentData).user
                    )
                }

                is UIState.Error -> { Text((uiState as UIState.Error).message) }
            }
        }
    }
}

@Composable
fun InfoView(
    profile: Profile,
    user: UserPrivate
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = shape,
                color = Locals.extras.border,
                width = 1.dp
            ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Locals.spacing.l),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(Locals.icons.largeLower)
                    .clip(CircleShape)
                    .background(colors.primary),
                contentAlignment = Alignment.Center
            ) {

            }

            Spacer(Modifier.height(Locals.spacing.s))

            Text(
                text = "${profile.firstName} ${profile.lastName}",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = colors.onSurface
            )

            Spacer(Modifier.height(Locals.spacing.xxs))

            Text(
                text = profile.position,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant
            )

            Text(
                text = profile.department,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant
            )
        }
    }

    Spacer(Modifier.height(Locals.spacing.m))

    Text(
        text = stringResource(R.string.contacts),
        style = MaterialTheme.typography.labelLarge,
        color = colors.onSurfaceVariant
    )

    Spacer(Modifier.height(8.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                color = Locals.extras.border,
                shape = shape,
                width = 1.dp
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        ProfileInfoRow(
            icon = PhosphorIcons.Regular.EnvelopeSimple,
            title = stringResource(R.string.email),
            subtitle = user.email
        )
        Divider(color = Locals.extras.border)
        ProfileInfoRow(
            icon = PhosphorIcons.Regular.Briefcase,
            title = stringResource(R.string.position),
            subtitle = profile.position
        )
        Divider(color = Locals.extras.border)
        ProfileInfoRow(
            icon = PhosphorIcons.Regular.Buildings,
            title = stringResource(R.string.department),
            subtitle = profile.department
        )
    }

    Spacer(Modifier.height(Locals.spacing.m))

    OutlinedButton(
        onClick = { /* TODO: logout */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.logOutButtonHeight),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = PhosphorIcons.Regular.SignOut,
            contentDescription = null,
            tint = colors.error
        )
        Spacer(Modifier.size(Locals.spacing.xs))
        Text(
            text = stringResource(R.string.log_out),
            color = colors.error,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Locals.spacing.xs,
                vertical = Locals.spacing.m
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Locals.icons.medium)
                .background(
                    colors.surfaceVariant.copy(alpha = 0.55f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Locals.spacing.xs),
                imageVector = icon,
                contentDescription = null,
                tint = colors.onSurfaceVariant
            )
        }

        Spacer(Modifier.width(Locals.spacing.xs))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp
                ),
                color = colors.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp
                ),
                color = colors.onSurfaceVariant
            )
        }
    }
}
