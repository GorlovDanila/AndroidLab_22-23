package com.example.androidlab_22_23.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidlab_22_23.MainActivity
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.model.Song

class CreateNotification {

    val channelId = "channel1"
    private var notification: Notification? = null

    fun createNotification(
        context: Context,
        song: Song,
        playButton: Int,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val icon = BitmapFactory.decodeResource(context.resources, song.cover)

            val intent = Intent(context, MainActivity::class.java)
                .putExtra("arg_id_value", song.id)
                .putExtra("arg_prev_id_value", song.id - 1)
                .putExtra("arg_next_id_value", song.id + 1)

            val pending = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_ONE_SHOT,
            )

            val pendingIntentPrevious: PendingIntent?
            val pendingIntentStop: PendingIntent?
            val intentPrevious: Intent = Intent(context, NotificationActionService::class.java)
                .setAction(MediaActions.PREVIOUS.toString())
            pendingIntentPrevious = PendingIntent.getBroadcast(
                context,
                0,
                intentPrevious,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val previous: Int = R.drawable.ic_baseline_skip_previous_24

            val intentStop: Intent = Intent(context, NotificationActionService::class.java)
                .setAction(MediaActions.STOP.toString())
            pendingIntentStop = PendingIntent.getBroadcast(
                context,
                0,
                intentStop,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val stop: Int = R.drawable.ic_baseline_stop_circle_24
            val intentPlay: Intent = Intent(context, NotificationActionService::class.java)
                .setAction(MediaActions.PLAY.toString())
            val pendingIntentPlay = PendingIntent.getBroadcast(
                context,
                0,
                intentPlay,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val pendingIntentNext: PendingIntent?

            val intentNext: Intent = Intent(context, NotificationActionService::class.java)
                .setAction(MediaActions.NEXT.toString())
            pendingIntentNext = PendingIntent.getBroadcast(
                context,
                0,
                intentNext,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val next: Int = R.drawable.ic_baseline_skip_next_24

            notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .setContentTitle(song.title)
                .setContentText(song.author)
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true) //show notification for only first time
                .setShowWhen(false)
                .addAction(previous, "Previous", pendingIntentPrevious)
                .addAction(playButton, "Play", pendingIntentPlay)
                .addAction(stop, "Stop", pendingIntentStop)
                .addAction(next, "Next", pendingIntentNext)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pending)
                .build()
            notificationManagerCompat.notify(1, notification!!)
        }
    }
}