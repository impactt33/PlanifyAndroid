package com.example.planify.main.navigation.screens.init_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.navigation.AppRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InitScreenViewModel(
    val authService: AuthService,
    val navHostController: NavHostController
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState.LOADING)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            if (!pingServer()) {
                _uiState.value = UIState.SERVER_NOT_AVAILABLE
                return@launch
            }
            if (isAuthorized()) {
                navHostController.navigate(AppRoute.Main.route)
            } else {
                navHostController.navigate(AppRoute.Login.route)
            }
        }
    }

    fun isAuthorized(): Boolean {
        return authService.isAuthorized()
    }

    suspend fun pingServer(): Boolean {
        delay(1000)
        return true
    }
}