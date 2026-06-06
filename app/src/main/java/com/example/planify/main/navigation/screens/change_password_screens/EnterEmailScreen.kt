package com.example.planify.main.navigation.screens.change_password_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.Envelope
import com.adamglin.phosphoricons.regular.LockOpen
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.navigation.AppRoute
import com.example.planify.main.navigation.screens.fixed_screens.ErrorScreen

@Composable
fun EnterEmailScreen(
    viewModel: EnterEmailScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    when(val screenUiState = uiState) {
        is EnterEmailScreenUIState.Error -> {
            ErrorScreen(
                status = screenUiState.message
            )
        }
        is EnterEmailScreenUIState.IsEmailCorrect -> {
            EnterEmailScreenContent(
                viewModel = viewModel,
                uiState = screenUiState,
                navController = navController
            )
        }
    }
}


@Composable
private fun EnterEmailScreenContent(
    viewModel: EnterEmailScreenViewModel,
    uiState: EnterEmailScreenUIState.IsEmailCorrect,
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when(action) {
                is EnterEmailScreenAction.NavigateToEmailConfirmation -> {
                    navController.navigate(
                        AppRoute.ChangePasswordEmailConfirm(action.challengeUUID)
                    )
                }
            }
        }
    }

    var emailQuery by remember { mutableStateOf("") }

    val blueGradient = Locals.gradients.blue

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Сброс пароля",
                style = MaterialTheme.typography.displayMedium,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(Locals.spacing.m))

            Text(
                text = "Введите адрес электронной почты, на которую мы отправим код для сброса пароля",
                style = MaterialTheme.typography.labelMedium,
                color = Locals.extras.mutedForeground
            )

            Spacer(modifier = Modifier.height(Locals.spacing.m))

            Icon(
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(Locals.icons.medium)
                    .graphicsLayer(
                        compositingStrategy = CompositingStrategy.Offscreen
                    )
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = blueGradient,
                            blendMode = BlendMode.SrcIn
                        )
                    },
                imageVector = PhosphorIcons.Regular.LockOpen,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(Locals.spacing.m))

            TextField(
                value = emailQuery,
                onValueChange = {
                    emailQuery = it
                    viewModel.checkEmailCorrectness(emailQuery)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .withShapeBackground(
                        color = colors.background,
                        shape = Locals.shapes.mediumShape
                    )
                    .padding(horizontal = Locals.spacing.l),
                label = { Text("Электронная почта") },
                placeholder = { Text("Введите ваш email") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(Locals.icons.smallPlus),
                        imageVector = PhosphorIcons.Regular.Envelope,
                        tint = colors.onBackground,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                ),
                singleLine = true,
                shape = Locals.shapes.mediumShape
            )

            Spacer(modifier = Modifier.height(Locals.spacing.m))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Locals.spacing.l),
                shape = Locals.shapes.mediumShape,
                onClick = {
                    viewModel.sendEmailIntent(emailQuery)
                },
                enabled = !uiState.isNotCorrect
            ) {
                Text(
                    text = "Отправить код",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onPrimary
                )
            }
        }
    }
}