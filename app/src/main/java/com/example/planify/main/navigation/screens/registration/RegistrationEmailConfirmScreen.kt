package com.example.planify.main.navigation.screens.registration

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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.planify.main.navigation.screens.fixed_screens.ErrorScreen
import kotlinx.coroutines.delay

@Composable
fun RegistrationEmailConfirmScreen(
    viewModel: RegistrationEmailConfirmViewModel = hiltViewModel(),
    verificationUserId: String?,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    when(val screenUiState = uiState) {
        is RegistrationEmailConfirmUIState.CodeInput -> {
            RegistrationEmailConfirmScreenContent(
                viewModel = viewModel,
                isInputIncorrect = screenUiState.isIncorrect,
                verificationUserId = verificationUserId,
                navController = navController
            )
        }
        is RegistrationEmailConfirmUIState.Error -> {
            ErrorScreen(status = screenUiState.message)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun RegistrationEmailConfirmScreenContent(
    viewModel: RegistrationEmailConfirmViewModel,
    verificationUserId: String?,
    isInputIncorrect: Boolean,
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme

    var code by rememberSaveable { mutableStateOf("") }

    val blueGradient = Locals.gradients.blue

    var timeLeft by remember { mutableIntStateOf(59) }

    var resendEnabled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        resendEnabled = true
    }

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when(action) {
                is RegistrationEmailConfirmAction.NavigateToMainScreen -> {
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = true }
                    }
                }
                is RegistrationEmailConfirmAction.NavigateToAuthScreen -> {
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
                text = "Подтверждение регистрации",
                style = MaterialTheme.typography.displayMedium,
                color = colors.onBackground
            )

            Spacer(modifier = Modifier.height(Locals.spacing.xs))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Locals.spacing.l),
                text = "Введите код из письма, которое мы отправили на почту",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Locals.spacing.xs))

            Text(
                text = "admin@examle.com",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.primary
            )

            Spacer(modifier = Modifier.height(Locals.spacing.l))

            CodeField(
                code = code,
                onCodeChange = {
                    code = it
                    viewModel.resetCodeCorrectness()
                },
                isError = isInputIncorrect
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
                    if (verificationUserId != null) {
                        viewModel.codeVerificationIntent(verificationUserId, code)
                    } else {
                        viewModel.codeVerificationIntent("", code)
                    }
                }
            ) {
                Text(
                    text = "Подтвердить",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(Locals.spacing.s))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Locals.dimens.authButtonHeight)
                    .padding(horizontal = Locals.spacing.l)
                    .background(
                        color = Color.Transparent,
                        shape = Locals.shapes.mediumShape
                    )
                    .border(
                        width = 1.dp,
                        shape = Locals.shapes.mediumShape,
                        brush = if (resendEnabled) blueGradient else Brush.linearGradient(colors = listOf(Locals.extras.mutedForeground, Locals.extras.mutedForeground))
                    ),
                shape = Locals.shapes.mediumShape,
                enabled = resendEnabled,
                onClick = {
                    timeLeft = 59
                    resendEnabled = false
                }
            ) {
                Text(
                    text = "Отправить код повторно",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (resendEnabled) colors.onPrimaryContainer else Locals.extras.mutedForeground
                )
            }

            Spacer(modifier = Modifier.height(Locals.spacing.xs))

            Text(
                text = "Повторная отправка через 00:${String.format("%02d", timeLeft)}",
                style = MaterialTheme.typography.bodySmall,
                color = Locals.extras.mutedForeground
            )

            Spacer(modifier = Modifier.height(Locals.spacing.s))

            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = Locals.spacing.l)
            )

            Spacer(modifier = Modifier.height(Locals.spacing.s))

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