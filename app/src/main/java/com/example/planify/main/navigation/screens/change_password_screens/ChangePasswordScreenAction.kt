package com.example.planify.main.navigation.screens.change_password_screens

sealed interface ChangePasswordScreenAction {
    object NavigateToAuthScreen: ChangePasswordScreenAction
}