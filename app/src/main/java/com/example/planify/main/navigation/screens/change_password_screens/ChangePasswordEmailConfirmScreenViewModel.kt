package com.example.planify.main.navigation.screens.change_password_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.common.network.exceptions.WrongCodeException
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

    fun codeVerificationIntent(challengeUUID: String?, verificationCode: Int) {
        viewModelScope.launch {
            if (challengeUUID == null) {
                _uiState.emit(ChangePasswordEmailConfirmScreenUIState.Error(
                    message = "missing challengeUUID"
                ))
                return@launch
            }

            authService.checkVerificationCode(
                confirmationUuid = challengeUUID,
                verificationCode = verificationCode
            ).fold(
                onSuccess = {
                    _actions.emit(ChangePasswordEmailConfirmScreenAction.NavigateToResetPasswordScreen(challengeUUID))
                },
                onFailure = { error ->
                    if (error is WrongCodeException) {
                        _uiState.emit(ChangePasswordEmailConfirmScreenUIState.CodeInput(
                            isIncorrect = true
                        ))
                    } else {
                        _uiState.emit(ChangePasswordEmailConfirmScreenUIState.Error(
                            message = error.message.toString()
                        ))
                    }
                }
            )
        }
    }

    fun resendVerificationCode(challengeUUID: String?) {
        viewModelScope.launch {
            if (challengeUUID == null) {
                _uiState.emit(ChangePasswordEmailConfirmScreenUIState.Error(
                    message = "missing challengeUUID"
                ))
                return@launch
            }

            authService.resendRecoverVerificationCode(challengeUUID)
                .onFailure { error ->
                    _uiState.emit(
                        ChangePasswordEmailConfirmScreenUIState.Error(error.message.toString())
                    )
                }
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
