package com.example.androidlab_22_23

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.*

class NotificationProvider(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val vibration = arrayOf(100L, 200L).toLongArray()
    private val color = Color.BLUE
    private val myAudioAttributes: AudioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build()
    private val sound: Uri = Uri.parse(
        "android.resource://" + context.packageName + "/" + R.raw.sound
    )

    fun showNotification() {

        val intent = Intent(context, SecondActivity::class.java)
        val pending = PendingIntent.getActivity(
            context,
            100,
            intent,
            PendingIntent.FLAG_ONE_SHOT,
        )

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.default_notification_channel_id)
            )
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentTitle("Будильник")
                .setContentText("Пора вставать!")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setVibrate(vibration)
                .setLights(color, 100, 500)
                .setSound(sound)
                .setContentIntent(pending)

        notificationManager.notify(21, builder.build())
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.default_notification_channel_id),
                context.getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = color
                vibrationPattern = vibration
                setSound(sound, myAudioAttributes)
            }.also {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    fun setAlarm(calendar: Calendar) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            pendingIntent
        )
    }
}