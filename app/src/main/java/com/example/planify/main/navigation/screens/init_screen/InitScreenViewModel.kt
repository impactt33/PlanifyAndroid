package com.example.planify.main.navigation.screens.init_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitScreenViewModel @Inject constructor(
    val authService: AuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState.LOADING)
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableSharedFlow<AppRoute>()
    val navigation = _navigation.asSharedFlow()

    init {
        checkAuth()
    }

    fun checkAuth() {
        viewModelScope.launch {
            if (!pingServer()) return@launch  // TODO
        }

        viewModelScope.launch {
            async { authService.readSavedAuthInfo() }.await()

            authService.authStateFlow.collect { state ->
                when (state) {
                    is AuthState.Authenticated -> _navigation.emit(AppRoute.Main)
                    is AuthState.Unauthenticated -> {  // _navigation.emit(AppRoute.Auth)
                        authService.login(
                            email = "admin@example.com",
                            password = "adminpass"
                        )
                    }

                    is AuthState.Loading -> {}
                }
            }
        }
    }

    fun isAuthenticated(): Boolean = authService.isAuthenticated()

    suspend fun pingServer(): Boolean {
        delay(1000)
        return true
    }
}