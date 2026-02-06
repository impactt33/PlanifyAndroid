package com.example.planify.main.navigation.screens.auth_screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CaretRight
import com.adamglin.phosphoricons.regular.Eye
import com.adamglin.phosphoricons.regular.Star
import com.adamglin.phosphoricons.regular.StarFour
import com.example.planify.R
import com.example.planify.main.common.themes.Locals
import com.example.planify.main.common.ui.withShapeBackground
import kotlin.math.max

@Composable
fun AuthScreen(
    onRegister: () -> Unit,
    onForgetPassword: () -> Unit,
    onAuth: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    var emailQuery by remember { mutableStateOf("") }
    var passwordQuery by remember { mutableStateOf("") }

    CloudyBackground(
        modifier = Modifier.fillMaxSize(),
        animate = true
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(Locals.spacing.l),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.welcome_back),
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.onBackground
                )

                Spacer(modifier = Modifier.height(Locals.spacing.l))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Locals.icons.small),
                        imageVector = PhosphorIcons.Regular.StarFour,
                        contentDescription = null,
                        tint = colors.secondary
                    )

                    Spacer(modifier = Modifier.width(Locals.spacing.xs))

                    Text(
                        text = stringResource(R.string.log_in_to_continue),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(Locals.spacing.m))

                LabeledTextField(
                    label = stringResource(R.string.email),
                    value = emailQuery,
                    onValueChange = { emailQuery = it },
                    placeholder = stringResource(R.string.email_example)
                )

                Spacer(modifier = Modifier.height(Locals.spacing.m))

                LabeledTextField(
                    label = stringResource(R.string.password),
                    value = passwordQuery,
                    onValueChange = { passwordQuery = it },
                    placeholder = stringResource(R.string.enter_password),
                    icon = PhosphorIcons.Regular.Eye
                )

                Spacer(modifier = Modifier.height(Locals.spacing.m))

                Text(
                    modifier = Modifier
                        .clickable(
                            onClick = onForgetPassword
                        )
                        .align(Alignment.End),
                    text = stringResource(R.string.forget_password),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.secondary
                )

                Spacer(modifier = Modifier.height(Locals.spacing.xl))

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.have_not_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onBackground
                    )

                    Spacer(modifier = Modifier.width(Locals.spacing.xs))

                    Text(
                        modifier = Modifier
                            .clickable(
                                onClick = onRegister
                            ),
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondary
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = Locals.spacing.l)
                    .height(Locals.dimens.authButtonHeight)
                    .background(
                        brush = Locals.gradients.blue,
                        shape = Locals.shapes.mediumShape
                    )
                    .align(Alignment.BottomCenter),
                shape = Locals.shapes.mediumShape,
                onClick = onAuth,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Locals.spacing.xxxs),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.enter),
                        style = MaterialTheme.typography.displaySmall,
                        color = colors.onPrimary
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        modifier = Modifier
                            .size(Locals.icons.smallPlus),
                        imageVector = PhosphorIcons.Regular.CaretRight,
                        contentDescription = null,
                        tint = colors.onPrimary
                    )
                }
            }
        }

    }
}

@Composable
private fun LabeledTextField(
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

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    val colors = MaterialTheme.colorScheme
    val shape = Locals.shapes.mediumShape

    var isVisible by remember { mutableStateOf(true) }

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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .size(Locals.icons.smallPlus)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isVisible = true
                                    try {
                                        awaitRelease()
                                    } finally {
                                        isVisible = false
                                    }
                                }
                            )
                        },
                    imageVector = icon,
                    contentDescription = null
                )
            },
            visualTransformation = if (isVisible) VisualTransformation.None
                else PasswordVisualTransformation()
        )
    }
}

private data class CloudSpec(
    val yFrac: Float,     // 0..1
    val xFrac: Float,     // 0..1 (стартовая позиция)
    val scale: Float,
    val alpha: Float,
    val speed: Float      // px/sec (примерно)
)

@Composable
fun CloudyBackground(
    modifier: Modifier = Modifier,
    cloudColor: Color = Color.White,
    tintColor: Color = Color(0xFFEEF4FF), // лёгкий холодный оттенок фона
    animate: Boolean = true
) {
    val clouds = remember {
        listOf(
            CloudSpec(yFrac = 0.18f, xFrac = 0.15f, scale = 1.10f, alpha = 0.28f, speed = 12f),
            CloudSpec(yFrac = 0.28f, xFrac = 0.75f, scale = 0.95f, alpha = 0.22f, speed = 9f),
            CloudSpec(yFrac = 0.58f, xFrac = 0.10f, scale = 1.25f, alpha = 0.18f, speed = 7f),
            CloudSpec(yFrac = 0.72f, xFrac = 0.82f, scale = 1.05f, alpha = 0.16f, speed = 6f),
            CloudSpec(yFrac = 0.88f, xFrac = 0.40f, scale = 1.40f, alpha = 0.14f, speed = 5f),
        )
    }

    val t = if (animate) {
        val tr = rememberInfiniteTransition(label = "clouds")
        tr.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 60000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "t"
        ).value
    } else 0f

    Canvas(modifier = modifier.fillMaxSize()) {
        // общий фон-тинт
        drawRect(color = tintColor)

        val w = size.width
        val h = size.height

        clouds.forEach { c ->
            // базовые размеры облака от ширины экрана
            val baseCloudW = w * 0.42f * c.scale
            val baseCloudH = baseCloudW * 0.36f

            val y = h * c.yFrac

            // простая “плавающая” анимация по X с wrap-around
            val drift = if (animate) (t * c.speed * 600f) else 0f // грубо: 60 секунд цикл
            val startX = w * c.xFrac
            val xWrapped = ((startX + drift) % (w + baseCloudW)) - baseCloudW * 0.5f
            val x = max(-baseCloudW, xWrapped)

            drawCloud(
                center = Offset(x, y),
                cloudW = baseCloudW,
                cloudH = baseCloudH,
                color = cloudColor
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCloud(
    center: Offset,
    cloudW: Float,
    cloudH: Float,
    color: Color
) {
    // “мягкость”: рисуем 2 слоя (чуть больше и прозрачнее + основной)
    fun layer(scale: Float, aMul: Float) {
        val w = cloudW * scale
        val h = cloudH * scale
        val left = center.x - w / 2f
        val top = center.y - h / 2f

        // основание (скруглённый прямоугольник)
        drawRoundRect(
            color = color.copy(alpha = color.alpha * aMul),
            topLeft = Offset(left, top + h * 0.28f),
            size = Size(w, h * 0.62f),
            cornerRadius = CornerRadius(h, h)
        )

        // “пузырьки” сверху
        val r1 = h * 0.38f
        val r2 = h * 0.48f
        val r3 = h * 0.34f

        drawCircle(color = color.copy(alpha = color.alpha * aMul), radius = r1, center = Offset(left + w * 0.30f, top + h * 0.52f))
        drawCircle(color = color.copy(alpha = color.alpha * aMul), radius = r2, center = Offset(left + w * 0.50f, top + h * 0.42f))
        drawCircle(color = color.copy(alpha = color.alpha * aMul), radius = r3, center = Offset(left + w * 0.70f, top + h * 0.54f))
    }

    layer(scale = 1.10f, aMul = 0.65f)
    layer(scale = 1.00f, aMul = 1.00f)
}