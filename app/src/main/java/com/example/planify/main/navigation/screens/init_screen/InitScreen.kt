package com.example.planify.main.navigation.screens.init_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.navigation.screens.init_screen.components.LoadingView

@Composable
private fun InitScreen(
    viewModel: InitScreenViewModel
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        UIState.LOADING -> LoadingView()
        UIState.SERVER_NOT_AVAILABLE -> Text("Error 500")
    }
}

@Composable
fun InitScreen(
    authService: AuthService,
    navHostController: NavHostController
) {
    val factory = remember { InitScreenViewModelFactory(
        authService = authService,
        navHostController = navHostController
    ) }
    InitScreen(viewModel = viewModel(factory = factory))
}

