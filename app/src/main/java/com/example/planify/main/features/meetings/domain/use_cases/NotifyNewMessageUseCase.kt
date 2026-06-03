package com.example.planify.main.features.meetings.domain.use_cases

import android.app.Notification
import com.example.planify.core.notifications.data.Notifier
import javax.inject.Inject

class NotifyNewMessageUseCase @Inject constructor(
    private val notifier: Notifier
) {
    operator fun invoke(message: Message) // TODO
}