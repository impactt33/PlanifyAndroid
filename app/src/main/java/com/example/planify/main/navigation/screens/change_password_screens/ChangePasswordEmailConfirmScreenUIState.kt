package com.example.planify.main.navigation.screens.change_password_screens

sealed interface ChangePasswordEmailConfirmScreenUIState {
    data class CodeInput(
        val isIncorrect: Boolean
    ): ChangePasswordEmailConfirmScreenUIState

    data class Error(
        val message: String
    ): ChangePasswordEmailConfirmScreenUIState
}