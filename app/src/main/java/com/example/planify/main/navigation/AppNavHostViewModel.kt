package com.example.planify.main.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.settings.domain.services.SettingsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppNavHostViewModel @Inject constructor(
    private val authService: AuthService,
    private val settingsService: SettingsService
) : ViewModel() {
    private val _events = MutableSharedFlow<NavHostUIEffect>()
    val effects = _events.asSharedFlow()

    init {
        getAuthState()
    }

    fun getAuthState() {
        var previousAuthState: AuthState? = null

        viewModelScope.launch {
            combine(
                authService.authStateFlow
                    .distinctUntilChangedBy { it::class },
                settingsService.settingsFlow
            ) { authState, settings ->
                authState to settings
            }.collect { state ->
                val (authState, settings) = state

                when (authState) {
                    is AuthState.Unauthenticated -> {
                        if (settings.isFirstStart || previousAuthState !is AuthState.Authenticated) {
                            _events.emit(NavHostUIEffect.Navigate(AppRoute.Auth))
                        } else {
                            _events.emit(NavHostUIEffect.ShowDialog(DialogType.AuthError))
                        }
                    }

                    is AuthState.Authenticated -> _events.emit(NavHostUIEffect.Navigate(AppRoute.Main))
                    is AuthState.Loading -> {}
                }

                previousAuthState = authState
            }
        }
    }
}
