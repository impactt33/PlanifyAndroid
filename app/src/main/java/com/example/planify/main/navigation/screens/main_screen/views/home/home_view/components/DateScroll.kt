package com.example.planify.main.navigation.screens.main_screen.views.home.home_view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.CaretLeft
import com.adamglin.phosphoricons.regular.CaretRight
import com.example.planify.main.common.themes.Locals
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

@Suppress("DEPRECATION")
@Composable
fun DateScroll(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val initialPage = 5000
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { 10000 }
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onDateSelected(LocalDate.now().plusDays((pagerState.currentPage - initialPage).toLong()))
    }

    val shape = RectangleShape
    val textFormat = selectedDate.format(ofPattern("EEEE, d MMMM", Locale("ru")))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(Locals.dimens.dateScrollBarHeight)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = true
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Locals.extras.border,
                        shape = RectangleShape
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier
                        .size(Locals.icons.medium)
                        .padding(horizontal = Locals.spacing.xs)
                        .clickable(
                            onClick = { }
                        ),
                    imageVector = PhosphorIcons.Regular.CaretLeft,
                    contentDescription = null
                )

                Text(
                    text = textFormat,
                    style = MaterialTheme.typography.titleMedium
                )

                Icon(
                    modifier = Modifier
                        .size(Locals.icons.medium)
                        .padding(horizontal = Locals.spacing.xs)
                        .clickable(
                            enabled = false,
                            onClick = { }
                        ),
                    imageVector = PhosphorIcons.Regular.CaretRight,
                    contentDescription = null
                )
            }
        }
    }
}