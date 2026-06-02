package com.example.planify.main.navigation.screens.change_password_screens

sealed interface ChangePasswordEmailConfirmScreenAction {
    object NavigateToResetPasswordScreen: ChangePasswordEmailConfirmScreenAction

    object NavigateToAuthScreen: ChangePasswordEmailConfirmScreenAction
}