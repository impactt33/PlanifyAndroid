package com.example.planify.main.navigation.screens.change_password_screen

sealed interface ChangePasswordScreenUIState {
    data class CodeInput(
        val isIncorrect: Boolean
    ): ChangePasswordScreenUIState

    data class Error(
        val message: String
    ): ChangePasswordScreenUIState
}