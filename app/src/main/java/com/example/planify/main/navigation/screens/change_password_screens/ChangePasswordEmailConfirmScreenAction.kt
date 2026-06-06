package com.example.planify.main.navigation.screens.change_password_screens

sealed interface ChangePasswordEmailConfirmScreenAction {
    data class NavigateToResetPasswordScreen(
        val challengeUUID: String
    ): ChangePasswordEmailConfirmScreenAction

    object NavigateToAuthScreen: ChangePasswordEmailConfirmScreenAction
}