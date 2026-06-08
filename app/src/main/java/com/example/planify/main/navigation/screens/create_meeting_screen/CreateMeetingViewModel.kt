package com.example.planify.main.navigation.screens.create_meeting_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planify.core.ui.state.ResourceState
import com.example.planify.main.features.meetings.domain.schemas.CreateMeetingSchema
import com.example.planify.main.features.meetings.domain.services.MeetingInvitesService
import com.example.planify.main.features.meetings.domain.services.MeetingsService
import com.example.planify.main.features.profiles.domain.entities.Profile
import com.example.planify.main.features.profiles.domain.services.ProfilesService
import com.example.planify.main.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime


@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    private val meetingsService: MeetingsService,
    private val meetingInvitesService: MeetingInvitesService,
    private val profilesService: ProfilesService
) : ViewModel() {

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()
    private val profilesFetchPageSize = 20

    private val _effects = MutableSharedFlow<UIEffect>()
    val effects = _effects.asSharedFlow()

    private val _meetingDraftState = MutableStateFlow(MeetingDraftState.empty())
    val meetingDraftState = _meetingDraftState.asStateFlow()

    private val _profilesSearchState = MutableStateFlow(ProfilesSearchState.empty())
    val profilesSearchState = _profilesSearchState.asStateFlow()

    private val _profilesSearchResourceState = MutableStateFlow<ResourceState<Map<Long, Profile>>>(ResourceState.Idle)
    val profilesSearchResultState = _profilesSearchResourceState.asStateFlow()

    private val _userScheduleResourceState = MutableStateFlow<ResourceState<Map<Int, Boolean>>>(ResourceState.Idle)
    val userScheduleResourceState = _userScheduleResourceState.asStateFlow()

    init {
        viewModelScope.launch {
            profilesSearchState
                .map { it.query }
                .distinctUntilChanged()
                .debounce(500L)
                .collectLatest { fetchProfiles(reset = true) }
        }
    }

    fun setName(v: String) = _meetingDraftState.update { it.copy(name = v) }
    fun setDescription(v: String) = _meetingDraftState.update { it.copy(description = v) }
    fun setLocation(v: String) = _meetingDraftState.update { it.copy(location = v) }
    fun setStartsAtDate(v: java.time.LocalDate) = _meetingDraftState.update { it.copy(startsAtDate = v) }
    fun setSelectedTimeSlots(v: List<Int>) = _meetingDraftState.update { it.copy(selectedTimeSlots = v) }
    fun removeInvite(userId: Long) = _meetingDraftState.update { it.copy(inviteUsersIds = it.inviteUsersIds - userId) }
    fun toggleInvite(userId: Long) = _meetingDraftState.update {
        it.copy(
            inviteUsersIds =
                if (it.inviteUsersIds.contains(userId)) it.inviteUsersIds - userId
                else it.inviteUsersIds + userId
        )
    }

    fun setProfilesQuery(query: String) = _profilesSearchState.update { it.copy(query = query) }

    suspend fun fetchUserSchedule() {
        meetingsService.fetchUserSchedule(_meetingDraftState.value.startsAtDate)
            .onSuccess { schedule -> _userScheduleResourceState.value = ResourceState.Success(schedule) }
            .onFailure { error -> Log.e(this::class.simpleName, "Error while fetching user schedule", error) }
    }

    suspend fun fetchProfiles(reset: Boolean = false) {
        val state = profilesSearchState.value
        if (state.query.isEmpty()) return

        profilesService.searchProfile(
            page = state.page,
            size = profilesFetchPageSize,
            query = state.query
        ).onSuccess { page ->
            val current = _profilesSearchResourceState.value
            val newItems = page.content.associateBy { it.userId }
            _profilesSearchResourceState.value =
                if (!reset && current is ResourceState.Success) {
                    ResourceState.Success(current.data + newItems)
                } else {
                    ResourceState.Success(newItems)
                }
        }.onFailure { exception ->
            _profilesSearchResourceState.value = ResourceState.Error(exception)
        }
    }

    suspend fun createMeeting() {
        val state = meetingDraftState.value

        // Явная валидация: раньше тут были name!!/description!!/location!! и общий catch,
        // из-за чего NPE или сетевая ошибка одинаково показывали "выбрано неверное время".
        val name = state.name
        val description = state.description
        val location = state.location
        val slots = state.selectedTimeSlots.sorted()

        if (name.isNullOrBlank() || description.isNullOrBlank() || location.isNullOrBlank() || slots.isEmpty()) {
            Log.w(this::class.simpleName, "createMeeting: некорректный черновик встречи")
            _showDialog.value = true
            return
        }

        val startsAt = state.startsAtDate.atTime(LocalTime.of(slots.first(), 0))

        val meeting = meetingsService.createMeeting(
            schema = CreateMeetingSchema(
                name = name,
                description = description,
                location = location,
                startsAt = startsAt,
                duration = slots.size
            )
        ).getOrElse { error ->
            Log.e(this::class.simpleName, "Error while creating meeting", error)
            _showDialog.value = true
            return
        }

        state.inviteUsersIds.forEach { userId ->
            meetingInvitesService.inviteUser(meeting.id, userId)
                .onFailure { Log.e(this::class.simpleName, "Error while sending invite to $userId", it) }
        }

        _effects.emit(UIEffect.Navigate(AppRoute.MeetingInfoMenu(meeting.id)))
    }

    fun runCreateMeeting() {
        viewModelScope.launch { createMeeting() }
    }

    fun canCreate(): Boolean {
        val state = _meetingDraftState.value
        return !state.name.isNullOrBlank() &&
                !state.description.isNullOrBlank() &&
                !state.location.isNullOrBlank() &&
                state.selectedTimeSlots.isNotEmpty()
    }

    fun incorrectTimeChosen() {
        _showDialog.value = true
    }

    fun closeDialog() {
        _showDialog.value = false
    }
}