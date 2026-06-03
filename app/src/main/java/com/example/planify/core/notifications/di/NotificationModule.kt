package com.example.planify.core.notifications.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.example.planify.core.notifications.data.AndroidNotifier
import com.example.planify.core.notifications.data.Notifier
import com.example.planify.core.notifications.domain.NotificationChannelSpec
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {
    @Binds
    @Singleton
    abstract fun bindNotifier(impl: AndroidNotifier): Notifier

    @Multibinds
    abstract fun notificationChannels(): Set<NotificationChannelSpec>

    companion object {
        @Provides
        @Singleton
        fun provideNotificationManager(
            @ApplicationContext context: Context
        ): NotificationManagerCompat = NotificationManagerCompat.from(context)
    }
}