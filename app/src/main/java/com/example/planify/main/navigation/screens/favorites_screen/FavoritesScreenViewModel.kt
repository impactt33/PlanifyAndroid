package com.example.planify.main.navigation.screens.favorites_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class FavoritesScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesScreenUIState.empty())
    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<FavoritesScreenUIEffect>()
    val effects = _effects.asSharedFlow()

    fun onIntent(intent: FavoritesScreenUIIntent) {
        when (intent) {
            is FavoritesScreenUIIntent.AddFavorite -> TODO()
            is FavoritesScreenUIIntent.RemoveFavorite -> TODO()
            is FavoritesScreenUIIntent.SearchQueryInput -> TODO()
        }
    }
}
