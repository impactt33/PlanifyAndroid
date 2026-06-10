package com.example.planify.main.navigation.screens.favorites_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.common.ui.add
import com.example.planify.main.common.ui.clearFocusOnTap
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.navigation.screens.favorites_screen.components.FavoriteCard
import com.example.planify.main.navigation.screens.favorites_screen.components.SearchSelection
import com.example.planify.main.navigation.screens.favorites_screen.entities.FavoriteRecordUIEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->

        }
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding.add(horizontal = 16.dp))
            .clearFocusOnTap(),
        isRefreshing = isRefreshing,
        onRefresh = {
            viewModel.viewModelScope.launch {
                isRefreshing = true
                delay(2000)
                isRefreshing = false
            }
        }
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

            items(10) {
                FavoriteCard(
                    favorite = FavoriteRecordUIEntity(
                        favoriteUserProfile = Profile(
                            userId = 123,
                            firstName = "asd",
                            lastName = "asd",
                            position = "asd",
                            department = "asd",
                            profileImageUrl = "https://dummyimage.com/512x512/000/fff"
                        ),
                        createdAt = "asdasdasdasd",
                        userId = 123,
                        starred = true
                    ),
                    onIntent = viewModel::onIntent
                )
            }

            item { Spacer(modifier = Modifier.size(16.dp)) }
        }
    }
}
