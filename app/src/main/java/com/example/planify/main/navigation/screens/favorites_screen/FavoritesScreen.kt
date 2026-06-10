package com.example.planify.main.navigation.screens.favorites_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.common.ui.add
import com.example.planify.main.common.ui.clearFocusOnTap
import com.example.planify.main.navigation.screens.favorites_screen.components.FavoriteCard
import com.example.planify.main.navigation.screens.favorites_screen.components.SearchSelection
import com.example.planify.main.navigation.screens.favorites_screen.entities.FavoriteRecordUIEntity

@Composable
fun FavoritesScreen(
    innerPadding: PaddingValues,
) {
    FavoritesScreen(
        viewModel = hiltViewModel(),
        innerPadding = innerPadding,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel,
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    val favorites: List<FavoriteRecordUIEntity> =
        (uiState.favorites as? ResourceState.Success)?.data?.values?.toList().orEmpty()

    val isRefreshing = uiState.favorites is ResourceState.Refreshing

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding.add(horizontal = 16.dp))
            .clearFocusOnTap(),
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.fetchFavorites(refresh = true) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stickyHeader {
                SearchSelection(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .height(56.dp),
                    query = uiState.query,
                    onIntent = viewModel::onIntent
                )
            }

            items(
                items = favorites,
                key = { it.userId }
            ) { favorite ->
                FavoriteCard(
                    favorite = favorite,
                    onIntent = viewModel::onIntent
                )
            }

            item { Spacer(modifier = Modifier.size(16.dp)) }
        }
    }
}
