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
class ChangePasswordEmailConfirmScreenViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel() {
    private val _uiState = MutableStateFlow<ChangePasswordEmailConfirmScreenUIState>(
        ChangePasswordEmailConfirmScreenUIState.CodeInput(
            isIncorrect = false
        )
    )

    val uiState = _uiState.asStateFlow()
    private val _actions = MutableSharedFlow<ChangePasswordEmailConfirmScreenAction>()

    val actions = _actions.asSharedFlow()

    init {
        codeSend()
    }

    fun codeSend() {
        viewModelScope.launch {
            authService.sendVerificationCode().fold(
                onSuccess = {
                    _uiState.emit(
                        ChangePasswordEmailConfirmScreenUIState.CodeInput(
                            isIncorrect = false
                        )
                    )
                },
                onFailure = { error ->
                    _uiState.emit(
                        ChangePasswordEmailConfirmScreenUIState.Error(
                            message = error.message.toString()
                        )
                    )
                }
            )
        }
    }

    fun codeVerificationIntent(verificationCode: String) {
        viewModelScope.launch {
            authService.checkVerificationCode(
                verificationCode = verificationCode
            ).fold(
                onSuccess = { result ->
                    if (result) {
                        _actions.emit(ChangePasswordEmailConfirmScreenAction.NavigateToResetPasswordScreen)
                    } else {
                        _uiState.emit(ChangePasswordEmailConfirmScreenUIState.CodeInput(isIncorrect = true))
                    }
                },
                onFailure = { error ->
                    _uiState.emit(ChangePasswordEmailConfirmScreenUIState.Error(message = error.message.toString()))
                }
            )
        }
    }

    fun resetCodeCorrectness() {
        viewModelScope.launch {
            _uiState.emit(
                ChangePasswordEmailConfirmScreenUIState.CodeInput(
                    isIncorrect = false
                )
            )
        }
    }

    fun goToAuthScreen() {
        viewModelScope.launch {
            _actions.emit(
                ChangePasswordEmailConfirmScreenAction.NavigateToAuthScreen
            )
        }
    }
}
