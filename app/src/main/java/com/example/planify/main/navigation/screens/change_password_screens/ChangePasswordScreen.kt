package com.example.planify.main.navigation.screens.change_password_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.LockOpen
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.navigation.AppRoute
import com.example.planify.main.navigation.screens.change_password_screens.components.CodeField
import com.example.planify.main.navigation.screens.change_password_screens.components.NewPasswordTextField
import com.example.planify.main.navigation.screens.fixed_screens.ErrorScreen
import kotlinx.coroutines.delay


@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordScreenViewModel = hiltViewModel(),
    challengeUUID: String?,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val screenUiState = uiState) {
        is ChangePasswordScreenUIState.Error -> ErrorScreen(status = screenUiState.message)
        is ChangePasswordScreenUIState.PasswordInput -> ChangePasswordScreenContent(
            viewModel = viewModel,
            challengeUUID = challengeUUID,
            navController = navController,
            uiState = screenUiState
        )
    }
}


@Composable
fun ChangePasswordScreenContent(
    viewModel: ChangePasswordScreenViewModel,
    challengeUUID: String?,
    navController: NavController,
    uiState: ChangePasswordScreenUIState.PasswordInput
) {
    val colors = MaterialTheme.colorScheme

    val blueGradient = Locals.gradients.blue

    var newPassword by remember { mutableStateOf("") }
    var newPasswordRepeat by remember { mutableStateOf("") }

    val firstFieldFocusRequester = remember { FocusRequester() }
    val secondFieldFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when(action) {
                ChangePasswordScreenAction.NavigateToAuthScreen -> {
                    navController.navigate(AppRoute.Auth.route) {
                        popUpTo(AppRoute.Auth.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(Locals.dimens.circleShieldIconSize)
                    .clip(CircleShape)
                    .background(
                        color = colors.primaryContainer,
                        shape = CircleShape
                    )
                    .border(
                        width = 3.dp,
                        brush = Locals.gradients.blue,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
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
            }

            Spacer(modifier = Modifier.height(Locals.spacing.l))

            Text(
                text = "Новый пароль",
                style = MaterialTheme.typography.displayMedium,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(Locals.spacing.xs))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Locals.spacing.l),
                text = "Придумайте новый пароль для входа в аккаунт",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Locals.spacing.l))

            NewPasswordTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    viewModel.checkForForbidden(newPassword)
                },
                imeAction = ImeAction.Next,
                onNext = { secondFieldFocusRequester.requestFocus() },
                isError = uiState.isForbidden || uiState.isNotMatch,
                placeholder = "Новый пароль",
                focusRequester = firstFieldFocusRequester
            )

            Spacer(modifier = Modifier.height(Locals.spacing.l))

            NewPasswordTextField(
                value = newPasswordRepeat,
                onValueChange = {
                    newPasswordRepeat = it
                    viewModel.checkForForbidden(newPasswordRepeat)
                },
                imeAction = ImeAction.Done,
                isError = uiState.isForbidden || uiState.isNotMatch,
                placeholder = "Повторите пароль",
                focusRequester = secondFieldFocusRequester
            )

            Spacer(modifier = Modifier.height(Locals.spacing.l))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Locals.spacing.l)
                    .height(Locals.dimens.authButtonHeight)
                    .background(
                        brush = blueGradient,
                        shape = Locals.shapes.mediumShape
                    ),
                shape = Locals.shapes.mediumShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    viewModel.confirmPasswordIntent(
                        newPassword = newPassword,
                        newPasswordRepeat = newPasswordRepeat,
                        challengeUUID = challengeUUID
                    )
                }
            ) {
                Text(
                    text = "Подтвердить",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(Locals.spacing.l))

            Text(
                modifier = Modifier.
                clickable {
                    viewModel.goToAuthScreen()
                },
                text = "Назад ко входу",
                style = MaterialTheme.typography.bodyLarge,
                color = Locals.extras.mutedForeground
            )
        }
    }
}