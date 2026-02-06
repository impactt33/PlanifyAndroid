package com.example.planify.main.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppNavHostViewModel @Inject constructor(
    val authService: AuthService
) : ViewModel() {
    private val _events = MutableSharedFlow<NavHostUIEffect>()
    val effects = _events.asSharedFlow()

    init {
        getAuthState()
    }

    fun getAuthState() {
        viewModelScope.launch {
            authService.authStateFlow
                .distinctUntilChangedBy { it::class }
                .collect { state ->
                    when (state) {
                        is AuthState.Unauthenticated -> _events.emit(NavHostUIEffect.ShowDialog(DialogType.AuthError))
                        is AuthState.Authenticated -> _events.emit(NavHostUIEffect.Navigate(AppRoute.Main))
                        is AuthState.Loading -> {}
                    }
                }
        }
    }
}
