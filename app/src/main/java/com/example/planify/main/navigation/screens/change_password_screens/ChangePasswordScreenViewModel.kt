package com.example.planify.main.navigation.screens.change_password_screens

import android.health.connect.datatypes.units.Length
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ChangePasswordScreenViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel() {
    private val _uiState = MutableStateFlow<ChangePasswordScreenUIState>(
        ChangePasswordScreenUIState.PasswordInput(
            isForbidden = false,
            isNotMatch = false
        )
    )

    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<ChangePasswordScreenAction>()

    val actions = _actions.asSharedFlow()

    fun confirmPasswordIntent(
        newPassword: String,
        newPasswordRepeat: String,
        challengeUUID: String?,
        minLength: Int = 8,
        maxLength: Int = 32
    ) {
        viewModelScope.launch {
            if (newPassword != newPasswordRepeat || newPassword.length < minLength || newPassword.length > maxLength) {
                _uiState.emit(
                    ChangePasswordScreenUIState.PasswordInput(
                        isForbidden = false,
                        isNotMatch = true
                    )
                )

                return@launch
            }

            if (challengeUUID == null) {
                _uiState.emit(
                    ChangePasswordScreenUIState.Error(
                        message = "challengeUUID is not specified"
                    )
                )
                return@launch
            }

            authService.resetPassword(
                newPassword = newPassword,
                challengeUUID = challengeUUID
            ).fold(
                onSuccess = {
                    _actions.emit(ChangePasswordScreenAction.NavigateToAuthScreen)
                },
                onFailure = { error ->
                    _uiState.emit(
                        ChangePasswordScreenUIState.Error(
                            message = error.message.toString()
                        )
                    )
                }
            )
        }
    }

    fun checkForForbidden(
        newPassword: String
    ) {
        val newPasswordFiltered = newPassword.filter {
            it.isDigit() || it.toString().matches(Regex("[a-zA-Z]"))
        }

        if (newPassword == newPasswordFiltered) {
            _uiState.value = ChangePasswordScreenUIState.PasswordInput(
                isForbidden = false,
                isNotMatch = false
            )
        } else {
            _uiState.value = ChangePasswordScreenUIState.PasswordInput(
                isForbidden = true,
                isNotMatch = false
            )
        }
    }

    fun goToAuthScreen() {
        viewModelScope.launch {
            _actions.emit(ChangePasswordScreenAction.NavigateToAuthScreen)
        }
    }
}