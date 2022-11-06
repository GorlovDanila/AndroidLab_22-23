package com.example.androidlab_22_23

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    private var calendar: Calendar? = null
    private var count = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!!.action == Intent.ACTION_BOOT_COMPLETED && count == 0) {
            count++
            var sharedPreferences = context?.getSharedPreferences("myTime", Context.MODE_PRIVATE)
            calendar?.timeInMillis = sharedPreferences!!.getLong("AlarmTime", 0)
            MainActivity().setAlarm(calendar!!)
        }

        val notificationProvider = NotificationProvider(context!!)
        notificationProvider.showNotification()
    }
}