package com.example.androidlab_22_23

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab_22_23.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.util.*

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var notificationProvider: NotificationProvider? = null
    private var timePicker: MaterialTimePicker? = null
    private var datePicker: MaterialDatePicker<Long>? = null
    private var calendar: Calendar? = null
    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = applicationContext.getSharedPreferences("myTime", Context.MODE_PRIVATE)
//        if(sharedPreferences!!.contains("AlarmTime")) {
//            calendar?.timeInMillis = sharedPreferences!!.getLong("AlarmTime", 0)
//            setAlarm()
//        }
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding?.run {
            tvTime.setOnClickListener {
                showTimePicker()
            }
            tvDate.setOnClickListener {
                showDatePicker()
            }
            btnStart.setOnClickListener {
                setAlarm(calendar!!)
            }
            btnEnd.setOnClickListener {
                cancelAlarm()
            }
        }

        notificationProvider = NotificationProvider(this)
        notificationProvider?.createNotificationChannel()
    }

    private fun showTimePicker() {
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .build()
        timePicker?.show(supportFragmentManager, "Time")

        timePicker!!.addOnPositiveButtonClickListener {
            calendar = Calendar.getInstance()
            calendar!!.set(Calendar.HOUR_OF_DAY, timePicker!!.hour)
            calendar!!.set(Calendar.MINUTE, timePicker!!.minute)
            Log.e("CALENDAR", calendar!!.timeInMillis.toString())
            sharedPreferences?.edit()?.putLong("AlarmTime", calendar!!.timeInMillis)?.apply()
            binding!!.tvTime.text = "Выбранное время: " + String.format(
                "%02d",
                (timePicker!!.hour)
            ) + " : " + String.format("%02d", timePicker!!.minute)
        }

        timePicker!!.addOnNegativeButtonClickListener {
            timePicker!!.dismiss()
        }
    }

    private fun showDatePicker() {
        datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker!!.show(supportFragmentManager, "Date")

        datePicker!!.addOnPositiveButtonClickListener {
            binding!!.tvDate.text = "Выбранная дата: ${datePicker!!.headerText}"
            val date: List<String> = datePicker?.headerText.toString().split(", ")
            val day: String = date.toString().split(" ")[1].split(",")[0]
            val month: Int = getMonth(date[0].split(" ")[0])
//            calendar = Calendar.getInstance()
//            calendar!!.set(Calendar.YEAR, date[1].toInt())
//            calendar!!.set(Calendar.DAY_OF_YEAR, day.toInt())
//            calendar!!.set(Calendar.MONTH, month)
            sharedPreferences?.edit()?.putLong("AlarmTime", calendar!!.timeInMillis)?.apply()
        }

        datePicker!!.addOnNegativeButtonClickListener {
            datePicker!!.dismiss()
        }
    }

    private fun getMonth(month: String): Int {
        val list = listOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )
        var position = 0
        for (i: Int in list.indices) {
            if (month == list[i]) {
                position = i + 1
            }
        }
        return position
    }

    fun setAlarm(calendar: Calendar) {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        if (alarmManager == null) {
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        }

        alarmManager!!.cancel(pendingIntent)
        calendar = null
        binding?.tvTime?.text = "Выберите время"
        binding?.tvDate?.text = "Выберите дату"
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationProvider = null
        binding = null
    }
}

