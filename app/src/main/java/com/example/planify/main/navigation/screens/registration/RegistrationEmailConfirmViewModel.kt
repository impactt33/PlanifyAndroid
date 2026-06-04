package com.example.planify.main.navigation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.common.network.exceptions.WrongCodeException
import com.example.planify.main.features.auth.domain.schemas.ConfirmRegisterUserSchema
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.settings.domain.services.SettingsService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationEmailConfirmViewModel @Inject constructor(
    private val authService: AuthService,
    private val settingsService: SettingsService
): ViewModel() {
    private val _uiState = MutableStateFlow<RegistrationEmailConfirmUIState>(
        RegistrationEmailConfirmUIState.CodeInput(
            isIncorrect = false
        )
    )

    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<RegistrationEmailConfirmAction>()

    val actions = _actions.asSharedFlow()

    init {
        sendCode()
    }

    fun sendCode() {
        viewModelScope.launch {
            authService.sendVerificationCode().fold(
                onSuccess = {
                    _uiState.emit(
                        RegistrationEmailConfirmUIState.CodeInput(
                            isIncorrect = false
                        )
                    )
                },
                onFailure = { error ->
                    _uiState.emit(
                        RegistrationEmailConfirmUIState.Error(
                            message = error.message.toString()
                        )
                    )
                }
            )
        }
    }

    fun codeVerificationIntent(verificationUserId: String, verificationCode: String) {
        viewModelScope.launch {
            authService.registerConfirmation(
                shema = ConfirmRegisterUserSchema(
                    verificationUserId = verificationUserId,
                    verificationCode = verificationCode
                )
            ).fold(
                onSuccess = {
                    _actions.emit(RegistrationEmailConfirmAction.NavigateToMainScreen)
                },
                onFailure = { error ->
                    if (error is WrongCodeException) {
                        _uiState.emit(RegistrationEmailConfirmUIState.CodeInput(isIncorrect = true))
                    } else {
                        _uiState.emit(RegistrationEmailConfirmUIState.Error(message = error.message.toString()))
                    }
                }
            )
        }
    }

    fun resetCodeCorrectness() {
        viewModelScope.launch {
            _uiState.emit(
                RegistrationEmailConfirmUIState.CodeInput(
                    isIncorrect = false
                )
            )
        }
    }

    fun goToAuthScreen() {
        viewModelScope.launch {
            _actions.emit(
                RegistrationEmailConfirmAction.NavigateToAuthScreen
            )
        }
    }
}