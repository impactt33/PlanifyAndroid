package com.example.planify.main.navigation.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.actions.data.sources.ActionsLocalDataSource
import com.example.planify.main.features.actions.domain.entities.Action
import com.example.planify.main.features.actions.domain.notifications.ActionNotificationHandler
import com.example.planify.main.features.actions.domain.services.ActionsService
import com.example.planify.main.features.auth.domain.entities.AuthState
import com.example.planify.main.features.auth.domain.services.AuthService
import com.example.planify.main.features.firebase_fcm.domain.registrar.FcmTokenRegistrar
import com.example.planify.main.features.settings.domain.services.SettingsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val actionsService: ActionsService,
    private val handlers: Set<@JvmSuppressWildcards ActionNotificationHandler>,
    private val actionsLocalDataSource: ActionsLocalDataSource,
    private val tokenRegistrar: FcmTokenRegistrar,
    private val authService: AuthService,
    private val settingsService: SettingsService
) : ViewModel() {
    private val _isFirstStartState = MutableStateFlow(true)
    val isFirstStart = _isFirstStartState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsService.settingsFlow.collect { settings ->
                _isFirstStartState.emit(settings.isFirstStart)
            }
        }

        viewModelScope.launch {
            authService.authStateFlow
                .map { it is AuthState.Authenticated }
                .distinctUntilChanged()
                .collect { authenticated ->
                    if (authenticated) tokenRegistrar.registerCurrentToken()
                }
        }

        viewModelScope.launch {
            actionsService.observeNewActions().collect { delta ->
                delta.forEach { action ->
                    if (actionsLocalDataSource.markActionNotifiedIfNewer(action.id)) {
                        handlers.find { action.type in it.supportedTypes }?.handle(action)
                    }
                }
            }
        }
    }

    fun sendFcmToken() {
        viewModelScope.launch { tokenRegistrar.registerCurrentToken() }
    }

    fun setIsFirstStartFalse() {
        viewModelScope.launch { _isFirstStartState.emit(false) }
    }

    fun onAction(action: Action<*>) {
        Log.i("Actions", "${action.type}: ${action.id}")
    }
}
