package com.example.planify.main.features.meetings.di

import com.example.planify.core.notifications.domain.NotificationChannelSpec
import com.example.planify.core.notifications.domain.NotificationImportance
import com.example.planify.main.features.meetings.domain.notifications.MeetingNotifications
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object MeetingsNotificationModule {

    @Provides
    @IntoSet
    fun provideMeetingsChannel(): NotificationChannelSpec =
        NotificationChannelSpec(
            id = MeetingNotifications.CHANNEL_ID,
            name = "Встречи",
            importance = NotificationImportance.HIGH
        )
}