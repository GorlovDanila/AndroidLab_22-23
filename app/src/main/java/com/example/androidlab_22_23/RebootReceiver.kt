package com.example.androidlab_22_23

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class RebootReceiver : BroadcastReceiver() {

    private var calendar: Calendar? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                val sharedPreferences =
                    context?.getSharedPreferences("myTime", Context.MODE_PRIVATE)
                calendar = Calendar.getInstance()
                if (sharedPreferences != null) {
                    calendar?.timeInMillis = sharedPreferences.getLong("AlarmTime", 0)
                    calendar?.set(Calendar.HOUR_OF_DAY, sharedPreferences.getInt("HOUR_OF_DAY", 0))
                    calendar?.set(Calendar.MINUTE, sharedPreferences.getInt("MINUTE", 0))
                    calendar?.set(Calendar.SECOND, 0)
                    calendar?.set(Calendar.MILLISECOND, 0)
                    calendar?.set(Calendar.YEAR, sharedPreferences.getInt("YEAR", 0))
                    calendar?.set(Calendar.DAY_OF_MONTH, sharedPreferences.getInt("DAY_OF_MONTH", 0)
                    )
                    calendar?.set(Calendar.MONTH, sharedPreferences.getInt("MONTH", 0))
                }
                if (context != null && calendar != null)
                    NotificationProvider(context).setAlarm(calendar!!)
            }
        }
    }
}