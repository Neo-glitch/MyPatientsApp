package com.neo.mypatients.core.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.neo.mypatients.R

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "patient_sync_channel"
        const val SYNC_FOREGROUND_INFO_NOTIFICATION_ID = 2
    }

    init {
        createNotificationChannel()
    }

    fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Sync Patients Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        ) .apply {
            description = "Notifications for data synchronization"
            setShowBadge(false)
            setSound(null, null)
        }

        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createSyncNotification(): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Syncing in progress")
            .setContentText("Pupils Sync in progress...")
            .setSmallIcon(R.drawable.ic_cloud_sync)
            .setOngoing(true)
            .setOnlyAlertOnce(false)
            .setProgress(0, 0, true)
            .build()
    }
}