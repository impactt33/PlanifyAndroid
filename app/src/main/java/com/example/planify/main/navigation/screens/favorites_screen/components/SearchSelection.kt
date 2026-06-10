package com.example.planify.main.navigation.screens.favorites_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Bold
import com.adamglin.phosphoricons.bold.MagnifyingGlass
import com.example.planify.R
import com.example.planify.main.navigation.screens.favorites_screen.FavoritesScreenUIIntent

@Composable
internal fun SearchSelection(
    modifier: Modifier = Modifier,
    query: String,
    onIntent: (FavoritesScreenUIIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val shape = RoundedCornerShape(16.dp)

    Surface(
        modifier = modifier.zIndex(999f),
        shadowElevation = 4.dp,
        shape = shape,
        contentColor = colors.surface
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { newQuery -> onIntent(FavoritesScreenUIIntent.SearchQueryInput(newQuery)) },
            placeholder = { Text(text = stringResource(R.string.step3_search_placeholder)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary,
                unfocusedIndicatorColor = colors.surface
            ),
            shape = shape,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = PhosphorIcons.Bold.MagnifyingGlass,
                    contentDescription = null,
                    tint = colors.onSurface
                )
            }
        )
    }
}
