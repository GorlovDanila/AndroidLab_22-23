package com.example.androidlab_22_23

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationProvider = NotificationProvider(context!!)
        notificationProvider.showNotification()
    }
}