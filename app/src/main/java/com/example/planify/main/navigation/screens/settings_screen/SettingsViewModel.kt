package com.example.planify.main.navigation.screens.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.settings.domain.services.SettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    val settingsService: SettingsService
): ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun getLocalSettings() {
        viewModelScope.launch {
            _uiState.emit(UIState.Loading)

            settingsService.getLocalSettings().fold(
                onSuccess = { data ->
                    _uiState.emit(
                        UIState.ContentData(
                            settings = data
                        )
                    )
                },
                onFailure = { error ->
                    _uiState.emit(UIState.Error(error.message.orEmpty()))
                }
            )
        }
    }

}