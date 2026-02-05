package com.example.planify.main.navigation.screens.main_screen.views.profile.ui.edit_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Camera
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.navigation.screens.main_screen.views.profile.ui.edit_profile.components.BottomBar
import com.example.planify.main.navigation.screens.main_screen.views.profile.ui.edit_profile.components.TopBar

val initialProfile = Profile(
    userId = 213L,
    firstName = "Anton",
    lastName = "Sus",
    position = "TL",
    department = "IT",
    profileImageUrl = "https://tsx.x5static.net/i/800x800-fit/xdelivery/files/06/65/cd01b821bea03c58564fxde5dcxd.jpg"
)

@Composable
fun EditProfile(
    profileInfo: Profile = initialProfile,
    onCameraClick: () -> Unit,
    onBack: () -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    var firstName by remember { mutableStateOf(initialProfile.firstName) }
    var lastName by remember { mutableStateOf(initialProfile.lastName) }
    var department by remember { mutableStateOf(initialProfile.position) }
    var email by remember { mutableStateOf("pidoras123@gmail.com") }
    var phoneNumber by remember { mutableStateOf("") }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopBar(
                onBack = onBack,
            )
        },
        bottomBar = {
            BottomBar(
                onSave = onSave,
                onCancel = onCancel
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        PaddingValues(
                            top = Locals.spacing.s,
                            start = Locals.spacing.m,
                            end = Locals.spacing.m
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(Locals.spacing.s)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            shape = shape,
                            color = Locals.extras.border,
                            width = 1.dp
                        )
                        .height(Locals.dimens.editProfileCardHeight),
                    shape = shape,
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Locals.spacing.l),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(Locals.icons.largePlus),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(Locals.icons.largePlus)
                                    .clip(CircleShape)
                                    .background(color = colors.primary.copy(alpha = 0.15f))
                                    .border(
                                        color = Locals.extras.border.copy(alpha = 0.02f),
                                        shape = CircleShape,
                                        width = 1.dp
                                    )
                                    .clickable {},
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .clip(CircleShape),
                                    model = profileInfo.profileImageUrl,
                                    contentDescription = null
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(Locals.icons.medium)
                                    .background(color = colors.primary)
                                    .clickable(
                                        onClick = onCameraClick
                                    )
                                    .align(Alignment.BottomEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(Locals.spacing.s),
                                    imageVector = PhosphorIcons.Regular.Camera,
                                    contentDescription = null,
                                    tint = colors.onPrimary
                                )
                            }
                        }

                        Spacer(Modifier.height(Locals.spacing.s))

                        Text(
                            text = stringResource(R.string.click_to_change_photo),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            color = Locals.extras.mutedForeground
                        )
                    }
                }

                InfoLabeledField(
                    label = stringResource(R.string.first_name),
                    value = firstName,
                    onValueChange = { firstName = it.replace("\n", "") },
                    placeholder = stringResource(R.string.first_name_placeholder)
                )

                InfoLabeledField(
                    label = stringResource(R.string.last_name),
                    value = lastName,
                    onValueChange = { lastName = it.replace("\n", "") },
                    placeholder = stringResource(R.string.last_name_placeholder)
                )

                InfoLabeledField(
                    label = stringResource(R.string.department),
                    value = department,
                    onValueChange = { department = it.replace("\n", "") },
                    placeholder = stringResource(R.string.department_placeholder)
                )

                InfoLabeledField(
                    label = stringResource(R.string.email),
                    value = email,
                    onValueChange = { email = it.replace("\n", "") },
                    placeholder = stringResource(R.string.email_placeholder)
                )

                InfoLabeledField(
                    label = stringResource(R.string.phone_number),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it.replace("\n", "") },
                    placeholder = stringResource(R.string.phone_number_placeholder)
                )

                InfoLabeledField(
                    label = stringResource(R.string.phone_number),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it.replace("\n", "") },
                    placeholder = stringResource(R.string.phone_number_placeholder)
                )

                InfoLabeledField(
                    label = stringResource(R.string.phone_number),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it.replace("\n", "") },
                    placeholder = stringResource(R.string.phone_number_placeholder)
                )

                Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
            }
        }
    }
}

@Composable
private fun InfoLabeledField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = colors.onBackground,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .withShapeBackground(
                    color = colors.background,
                    shape = shape
                ),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary,
                unfocusedIndicatorColor = colors.surface
            ),
            shape = shape,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}