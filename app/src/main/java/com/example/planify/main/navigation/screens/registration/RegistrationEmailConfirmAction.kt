package com.example.planify.main.navigation.screens.registration

sealed interface RegistrationEmailConfirmAction {
    object NavigateToMainScreen: RegistrationEmailConfirmAction

    object NavigateToAuthScreen: RegistrationEmailConfirmAction
}