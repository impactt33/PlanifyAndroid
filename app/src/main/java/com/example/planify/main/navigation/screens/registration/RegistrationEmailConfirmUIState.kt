package com.example.planify.main.navigation.screens.registration

sealed interface RegistrationEmailConfirmUIState {
    data class CodeInput(
        val isIncorrect: Boolean
    ): RegistrationEmailConfirmUIState

    data class Error(
        val message: String
    ): RegistrationEmailConfirmUIState
}