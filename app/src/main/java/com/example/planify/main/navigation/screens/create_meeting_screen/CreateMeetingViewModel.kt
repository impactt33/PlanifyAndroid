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
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val profilesFetchPageSize = 20
    private val profilesFetchSort = listOf("firstName,asc")

    private val _events = MutableSharedFlow<UIEvent>()
    val events = _events.asSharedFlow()

    private val _meetingDraftState = MutableStateFlow(MeetingDraftState.empty())
    val meetingDraftState = _meetingDraftState.asStateFlow()

    private val _profilesSearchState = MutableStateFlow(ProfilesSearchState.empty())
    val profilesSearchState = _profilesSearchState.asStateFlow()

    private val _profilesSearchResourceState = MutableStateFlow<ResourceState<Map<Long, Profile>>>(ResourceState.Idle)
    val profilesSearchResultState = _profilesSearchResourceState.asStateFlow()

    private val _userScheduleResourceState = MutableStateFlow<ResourceState<Map<Int, Boolean>>>(ResourceState.Idle)
    val userScheduleResourceState = _userScheduleResourceState.asStateFlow()

    fun setName(v: String) = _meetingDraftState.update { it.copy(name = v) }
    fun setDescription(v: String) = _meetingDraftState.update { it.copy(description = v) }
    fun setLocation(v: String) = _meetingDraftState.update { it.copy(location = v) }
    fun setStartsAtDate(v: LocalDate) = _meetingDraftState.update { it.copy(startsAtDate = v) }
    fun toggleTimeSlot(v: Int) = _meetingDraftState.update { it.copy(selectedTimeSlots = if (it.selectedTimeSlots.contains(v)) it.selectedTimeSlots - v else it.selectedTimeSlots + v) }
    fun replaceTimeSlot(v1: Int, v2: Int) = _meetingDraftState.update { it.copy(selectedTimeSlots = it.selectedTimeSlots - v1 + v2) }
    fun removeInvite(userId: Long) = _meetingDraftState.update { it.copy(inviteUsersIds = it.inviteUsersIds - userId) }
    fun toggleInvite(userId: Long) = _meetingDraftState.update {
        it.copy(
            inviteUsersIds =
                if (it.inviteUsersIds.contains(userId))
                    it.inviteUsersIds - userId
                else
                    it.inviteUsersIds + userId
        )
    }

    fun setProfilesQuery(query: String) = _profilesSearchState.update { it.copy(query = query) }

    suspend fun fetchUserSchedule() {
        meetingsService.fetchUserSchedule(_meetingDraftState.value.startsAtDate).onSuccess { schedule ->
            _userScheduleResourceState.value = ResourceState.Success(schedule)
        }
    }

    suspend fun fetchProfiles(reset: Boolean = false) {
        val state = profilesSearchState.value

        profilesService.searchProfile(
            page = state.page,
            size = profilesFetchPageSize,
            sort = profilesFetchSort,
            query = state.query
        ).onSuccess { page ->
            if (!reset && _profilesSearchResourceState.value is ResourceState.Success) {
                val old = (_profilesSearchResourceState.value as ResourceState.Success<Map<Long, Profile>>).data
                val new = page.content.associateBy { it.userId }
                _profilesSearchResourceState.value = ResourceState.Success(old + new)
            } else {
                _profilesSearchResourceState.value = ResourceState.Success(page.content.associateBy { it.userId })
            }
        }.onFailure { exception ->
            _profilesSearchResourceState.value = ResourceState.Error(exception)
        }
    }

    suspend fun createMeeting() {
        val state = meetingDraftState.value

        try {
            val meeting = meetingsService.createMeeting(
                schema = CreateMeetingSchema(
                    name = state.name!!,
                    description = state.description!!,
                    location = state.location!!,
                    startsAt = state.startsAtDate.atTime(LocalTime.of(state.selectedTimeSlots.sorted()[0], 0)),
                    duration = state.selectedTimeSlots.size
                )
            ).getOrThrow()

            state.inviteUsersIds.forEach { userId ->
                try {
                    meetingInvitesService.inviteUser(meeting.id, userId)
                } catch (error: Exception) {
                    Log.e(this::class.simpleName, "Error while sending invite", error)
                }
            }
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error while creating meeting", e)
        }
    }

    fun runCreateMeeting() {
        viewModelScope.launch { createMeeting() }
    }
}
