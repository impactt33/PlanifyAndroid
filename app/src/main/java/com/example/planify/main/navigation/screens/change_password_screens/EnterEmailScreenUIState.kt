package com.example.planify.main.navigation.screens.change_password_screens

sealed interface EnterEmailScreenUIState {
    data class IsEmailCorrect(
        val isNotCorrect: Boolean
    ): EnterEmailScreenUIState

    data class Error(
        val message: String
    ): EnterEmailScreenUIState
}