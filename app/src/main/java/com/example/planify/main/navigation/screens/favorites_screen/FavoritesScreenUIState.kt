package com.example.planify.main.navigation.screens.favorites_screen

import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.navigation.screens.favorites_screen.entities.FavoriteRecordUIEntity

internal data class FavoritesScreenUIState(
    val query: String,
    val favorites: ResourceState<Map<Long, FavoriteRecordUIEntity>>// TODO: Pagination
) {
    companion object {
        fun empty(): FavoritesScreenUIState = FavoritesScreenUIState(
            query = "",
            favorites = ResourceState.Idle
        )
    }
}
