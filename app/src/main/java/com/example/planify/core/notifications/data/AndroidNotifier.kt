package com.example.planify.core.notifications.data

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.planify.R
import com.example.planify.core.notifications.domain.AppNotification
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AndroidNotifier @Inject constructor (
    @ApplicationContext private val context: Context,
    private val manager: NotificationManagerCompat
): Notifier {
    override fun show(notification: AppNotification) {
        val builder = NotificationCompat.Builder(context, notification.channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)

        notification.deepLink?.let {
            builder.setContentIntent(
                buildPendingIntent(notification.id, it)
            )
        }

        val canPost = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

        if (canPost) manager.notify(notification.id, builder.build())
    }

    override fun cancel(id: Int) = manager.cancel(id)

    private fun buildPendingIntent(id: Int, deepLink: String): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW, deepLink.toUri()).apply {
            setPackage(context.packageName)
        }
        return PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}