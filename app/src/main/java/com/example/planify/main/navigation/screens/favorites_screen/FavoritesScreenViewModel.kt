package com.example.planify.main.navigation.screens.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.features.favorites.domain.entities.FavoriteRecord
import com.example.planify.main.features.favorites.domain.services.FavoritesService
import com.example.planify.main.navigation.screens.favorites_screen.entities.FavoriteRecordUIEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoritesScreenViewModel @Inject constructor(
    private val favoritesService: FavoritesService
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesScreenUIState.empty())
    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<FavoritesScreenUIEffect>()
    val effects = _effects.asSharedFlow()

    private var searchJob: Job? = null

    init {
        fetchFavorites()
    }

    fun onIntent(intent: FavoritesScreenUIIntent) {
        when (intent) {
            is FavoritesScreenUIIntent.AddFavorite -> addFavorite(intent.favoriteUserId)
            is FavoritesScreenUIIntent.RemoveFavorite -> removeFavorite(intent.favoriteUserId)
            is FavoritesScreenUIIntent.SearchQueryInput -> onSearchQueryInput(intent.query)
        }
    }

    fun fetchFavorites(refresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(favorites = if (refresh) ResourceState.Refreshing else ResourceState.Loading) }

            favoritesService.getFavorites()
                .onSuccess { records ->
                    _uiState.update { it.copy(favorites = ResourceState.Success(records.toUiMap())) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(favorites = ResourceState.Error(error)) }
                }
        }
    }

    private fun onSearchQueryInput(query: String) {
        _uiState.update { it.copy(query = query) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS)
            fetchFavorites()
        }
    }

    private fun addFavorite(favoriteUserId: Long) {
        viewModelScope.launch {
            setStarred(favoriteUserId, starred = true)

            favoritesService.addFavorite(favoriteUserId)
                .onFailure { setStarred(favoriteUserId, starred = false) }
        }
    }

    private fun removeFavorite(favoriteUserId: Long) {
        viewModelScope.launch {
            setStarred(favoriteUserId, starred = false)

            favoritesService.removeFavorite(favoriteUserId)
                .onFailure { setStarred(favoriteUserId, starred = true) }
        }
    }

    private fun setStarred(favoriteUserId: Long, starred: Boolean) {
        _uiState.update { state ->
            val current = (state.favorites as? ResourceState.Success)?.data ?: return@update state
            val record = current[favoriteUserId] ?: return@update state

            state.copy(favorites = ResourceState.Success(current + (favoriteUserId to record.copy(starred = starred))))
        }
    }

    private fun List<FavoriteRecord>.toUiMap(): Map<Long, FavoriteRecordUIEntity> =
        associate { record -> record.favoriteUserId to record.toUiEntity() }

    private fun FavoriteRecord.toUiEntity(): FavoriteRecordUIEntity = FavoriteRecordUIEntity(
        favoriteUserProfile = favoriteUserProfile,
        createdAt = createdAt.toString(),
        userId = favoriteUserId,
        starred = true
    )

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 400L
    }
}
