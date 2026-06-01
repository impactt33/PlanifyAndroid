package com.example.planify.main.navigation.screens.change_password_screen

sealed interface ChangePasswordScreenAction {
    object NavigateToResetPasswordScreen: ChangePasswordScreenAction

    object NavigateToAuthScreen: ChangePasswordScreenAction
}