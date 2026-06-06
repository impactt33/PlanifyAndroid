package com.example.planify.main.navigation.screens.change_password_screens

sealed interface EnterEmailScreenAction {
    data class NavigateToEmailConfirmation(
        val challengeUUID: String
    ): EnterEmailScreenAction

    data object NavigateToAuthScreen: EnterEmailScreenAction
}