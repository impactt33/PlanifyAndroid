package com.example.planify.main.navigation.screens.main_screen.views.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.auth.domain.services.UsersService
import com.example.planify.main.features.profile.domain.services.ProfilesService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    val profilesService: ProfilesService,
    val usersService: UsersService
): ViewModel() {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    suspend fun getOrFetchUserInfo() {
        delay(1000)

        viewModelScope.launch {
            profilesService.fetchMyProfile().fold(
                onSuccess = { profile ->
                    _uiState.value = UIState.ContentData(
                        profile = profile
                    )
                },
                onFailure = { error ->
                    _uiState.value = UIState.Error(error.message!!)
                }
            )
        }
    }
}