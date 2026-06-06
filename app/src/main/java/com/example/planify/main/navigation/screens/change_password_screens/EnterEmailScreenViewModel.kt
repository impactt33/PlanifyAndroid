package com.example.planify.main.navigation.screens.change_password_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EnterEmailScreenViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    private val _uiState = MutableStateFlow<EnterEmailScreenUIState>(EnterEmailScreenUIState.IsEmailCorrect(isNotCorrect = true))

    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<EnterEmailScreenAction>()

    val actions = _actions.asSharedFlow()

    fun checkEmailCorrectness(email: String) {
        if (!email.contains('@')) { // TODO
            _uiState.value = (
                EnterEmailScreenUIState.IsEmailCorrect(isNotCorrect = true)
            )
        } else {
            _uiState.value = (
                EnterEmailScreenUIState.IsEmailCorrect(isNotCorrect = false)
            )
        }
    }

    fun sendEmailIntent(email: String) {
        viewModelScope.launch {
            authService.sendVerificationCode(email)
                .onSuccess { challengeUUID ->
                    _actions.emit(EnterEmailScreenAction.NavigateToEmailConfirmation(challengeUUID))
                }
                .onFailure { error ->
                    _uiState.emit(
                        EnterEmailScreenUIState.Error(
                            message = error.message.toString()
                        )
                    )
                }
        }
    }

    fun goToAuth() {
        viewModelScope.launch {
            _actions.emit(EnterEmailScreenAction.NavigateToAuthScreen)
        }
    }
}