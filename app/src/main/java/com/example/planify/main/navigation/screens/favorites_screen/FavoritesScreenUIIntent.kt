package com.example.planify.main.navigation.screens.favorites_screen

internal sealed interface FavoritesScreenUIIntent {
    data class SearchQueryInput(val query: String) : FavoritesScreenUIIntent
    data class AddFavorite(val favoriteUserId: Long) : FavoritesScreenUIIntent
    data class RemoveFavorite(val favoriteUserId: Long) : FavoritesScreenUIIntent
}
