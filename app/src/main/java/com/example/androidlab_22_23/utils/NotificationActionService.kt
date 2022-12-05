package com.example.androidlab_22_23.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent("TRACKS_TRACKS").apply {
            putExtra("ACTION_NAME", intent!!.action)
        })
    }
}