package com.example.planify.main.navigation.screens.create_meeting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingInvitesService
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import com.example.planify.main.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate
import java.time.LocalTime


data class CreateMeetingDraft(
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val startsAtDate: LocalDate = LocalDate.now(),
    val startsAtTime: LocalTime = LocalTime.now(),
    val invitedUsersIds: Set<Long> = emptySet()
)

data class ProfilesSearchState(
    val query: String = "",
    val items: List<Profile> = emptyList(),
    val page: Int = 0,
    val last: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)


@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    private val meetingsService: MeetingsService,
    private val meetingInvitesService: MeetingInvitesService,
    private val profilesService: ProfilesService
): ViewModel() {
    private val _draft = MutableStateFlow(CreateMeetingDraft())
    val draft = _draft.asStateFlow()

    private val _profilesSearch = MutableStateFlow(ProfilesSearchState())
    val profilesSearch = _profilesSearch.asStateFlow()

    private val _navigation = MutableSharedFlow<AppRoute>()
    val navigation = _navigation.asSharedFlow()

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Creating)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun setName(v: String) = _draft.update { it.copy(name = v) }
    fun setDescription(v: String) = _draft.update { it.copy(description = v) }
    fun setLocation(v: String) = _draft.update { it.copy(location = v) }
    fun setStartsAtDate(v: LocalDate) = _draft.update { it.copy(startsAtDate = v) }
    fun setStartsAtTime(v: LocalTime) = _draft.update { it.copy(startsAtTime = v) }

    fun toggleInvite(profile: Long) = _draft.update {
        val newSet = if (profile in it.invitedUsersIds) it.invitedUsersIds - profile else it.invitedUsersIds + profile
        it.copy(invitedUsersIds = newSet)
    }

    fun setProfilesQuery(query: String) {
        _profilesSearch.update { it.copy(query = query) }
    }

    fun refreshProfiles() = loadNextProfiles(reset = true)

    fun loadNextProfiles(reset: Boolean = false) {
        val state = _profilesSearch.value
        if (state.isLoading) return
        if (!reset && state.last) return

        val query = state.query.trim()
        val nextPage = if (reset) 0 else state.page + 1
        val size = 20
        val sort = listOf("firstName,asc")

        viewModelScope.launch {
            _profilesSearch.update { it.copy(isLoading = true, error = null) }

            profilesService.searchProfile(
                page = nextPage,
                size = size,
                sort = sort,
                query = query
            ).onSuccess { pageResult ->
                _profilesSearch.update {
                    it.copy(
                        items = if (reset) pageResult.content else it.items + pageResult.content,
                        page = pageResult.number,
                        last = pageResult.last,
                        isLoading = false
                    )
                }
            }.onFailure { e ->
                _profilesSearch.update { it.copy(isLoading = false, error = e.message ?: "Search error") }
            }
        }
    }

    fun createMeeting(invitedUsersIds: List<Long>, schema: CreateMeetingSchema) {
        viewModelScope.launch {
            meetingsService.createMeeting(schema = schema)
                .onSuccess { createdMeeting ->
                    supervisorScope {
                        val result = invitedUsersIds.map { targetId ->
                            async {
                                meetingInvitesService.inviteUser(
                                    meetingId = createdMeeting.id,
                                    targetId = targetId
                                )
                            }
                        }.awaitAll()

                        val failed = result.firstOrNull { it.isFailure }
                        if (failed != null) {
                            _uiState.emit(UIState.Error(failed.exceptionOrNull()?.message ?: "Invite error"))
                            return@supervisorScope
                        }

                    }


                    _navigation.emit(AppRoute.MeetingInfoMenu(createdMeeting.id))
                }
                .onFailure { error ->
                    _uiState.emit(UIState.Error(error.message ?: "Runtime error"))
                }
        }
    }

}