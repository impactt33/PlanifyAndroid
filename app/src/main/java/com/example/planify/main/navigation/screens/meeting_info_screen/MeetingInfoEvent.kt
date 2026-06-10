package com.example.planify.main.navigation.screens.meeting_info_screen

sealed interface MeetingInfoEvent {
    data class ShowToast(val message: String) : MeetingInfoEvent
}