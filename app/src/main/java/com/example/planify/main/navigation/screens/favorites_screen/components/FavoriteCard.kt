package com.example.planify.main.navigation.screens.favorites_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.fill.Star
import com.adamglin.phosphoricons.regular.Star
import com.example.planify.main.common.ui.CustomShapes.StarShape
import com.example.planify.main.common.ui.muted
import com.example.planify.main.common.ui.objectClickable
import com.example.planify.main.common.ui.withShapeBackground
import com.example.planify.main.navigation.screens.favorites_screen.FavoritesScreenUIIntent
import com.example.planify.main.navigation.screens.favorites_screen.entities.FavoriteRecordUIEntity

@Composable
internal fun FavoriteCard(
    favorite: FavoriteRecordUIEntity,
    onIntent: (FavoritesScreenUIIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .withShapeBackground(
                color = colors.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(percent = 50)),
                contentDescription = null,
                model = favorite.favoriteUserProfile.profileImageUrl
            )

            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = favorite.favoriteUserProfile.fullName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = favorite.favoriteUserProfile.position,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurface.muted(alpha = 0.7f),
                    fontWeight = FontWeight.Normal
                )

                Box(
                    modifier = Modifier
                        .withShapeBackground(
                            shape = RoundedCornerShape(percent = 50),
                            color = colors.primary.copy(alpha = 0.2f)
                        )
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                ) {
                    Text(
                        text = favorite.favoriteUserProfile.department,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.primary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }

        Icon(
            modifier = Modifier
                .size(28.dp)
                .clip(StarShape)
                .objectClickable {
                    onIntent(
                        if (favorite.starred)
                            FavoritesScreenUIIntent.RemoveFavorite(favoriteUserId = favorite.userId)
                        else
                            FavoritesScreenUIIntent.AddFavorite(favoriteUserId = favorite.userId)
                    )
                },
            imageVector = if (favorite.starred) PhosphorIcons.Fill.Star else PhosphorIcons.Regular.Star,
            contentDescription = null,
            tint = colors.primary,
        )
    }
}
