package com.example.planify.core.notifications.data

import android.app.NotificationManager
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.example.planify.core.notifications.domain.NotificationChannelSpec
import com.example.planify.core.notifications.domain.NotificationImportance
import jakarta.inject.Inject

class NotificationChannelRegistrar @Inject constructor(
    private val manager: NotificationManagerCompat,
    private val specs: Set<@JvmSuppressWildcards NotificationChannelSpec>
) {
    fun registerAll() {
        specs.forEach { spec ->
            val channel = NotificationChannelCompat.Builder(spec.id, spec.importance.toPlatform())
                .setName(spec.name)
                .build()
            manager.createNotificationChannel(channel)
        }
    }

    private fun NotificationImportance.toPlatform(): Int = when (this) {
        NotificationImportance.HIGH -> NotificationManager.IMPORTANCE_HIGH
        NotificationImportance.DEFAULT -> NotificationManager.IMPORTANCE_DEFAULT
        NotificationImportance.LOW -> NotificationManager.IMPORTANCE_LOW
        NotificationImportance.MIN -> NotificationManager.IMPORTANCE_MIN
    }
}