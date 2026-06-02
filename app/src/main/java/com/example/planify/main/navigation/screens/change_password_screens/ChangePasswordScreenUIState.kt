package com.example.planify.main.navigation.screens.change_password_screens

sealed interface ChangePasswordScreenUIState {
    data class PasswordInput(
        val isForbidden: Boolean,
        val isNotMatch: Boolean
    ): ChangePasswordScreenUIState

    data class Error(
        val message: String
    ): ChangePasswordScreenUIState
}