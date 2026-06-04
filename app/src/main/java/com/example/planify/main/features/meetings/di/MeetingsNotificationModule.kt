package com.example.planify.main.features.meetings.di

import com.example.planify.core.notifications.domain.NotificationChannelSpec
import com.example.planify.core.notifications.domain.NotificationImportance
import com.example.planify.main.features.actions.domain.notifications.ActionNotificationHandler
import com.example.planify.main.features.meetings.domain.notifications.MeetingActionNotificationHandler
import com.example.planify.main.features.meetings.domain.notifications.MeetingNotifications
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class MeetingsNotificationModule {
    @Binds
    @IntoSet
    abstract fun bindMeetingHandler(
        impl: MeetingActionNotificationHandler
    ): ActionNotificationHandler

    companion object {
        @Provides
        @IntoSet
        fun provideMeetingsChannel(): NotificationChannelSpec =
            NotificationChannelSpec(
                id = MeetingNotifications.CHANNEL_ID,
                name = "Встречи",
                importance = NotificationImportance.HIGH
            )
    }
}