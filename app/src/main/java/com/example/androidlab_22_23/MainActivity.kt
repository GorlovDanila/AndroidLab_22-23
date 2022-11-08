package com.example.androidlab_22_23

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
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
    private var flagTime: Boolean = false
    private var flagDate: Boolean = false
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = applicationContext.getSharedPreferences("myTime", Context.MODE_PRIVATE)

        notificationProvider = NotificationProvider(this)
        notificationProvider?.createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding?.run {
            tvTime.setOnClickListener {
                flagTime = true
                showTimePicker()
            }
            tvDate.setOnClickListener {
                flagDate = true
                showDatePicker()
            }
            btnStart.setOnClickListener {
                if (!flagTime && !flagDate) {
                    toast = Toast.makeText(
                        this@MainActivity,
                        "Для назначения будильника необходимо настроить дату и время!",
                        Toast.LENGTH_LONG
                    )
                    toast!!.show()
                }
                if (!flagDate && flagTime) {
                    toast = Toast.makeText(
                        this@MainActivity,
                        "Дата не выбрана, поэтому будильник сработает сегодня!",
                        Toast.LENGTH_LONG
                    )
                    toast!!.show()
                    notificationProvider?.setAlarm(calendar!!)
                }
                if (!flagTime) {
                    toast = Toast.makeText(this@MainActivity, "Выберите время!", Toast.LENGTH_LONG)
                    toast!!.show()
                }
                if (flagTime && flagDate) {
                    notificationProvider?.setAlarm(calendar!!)
                }
            }
            btnEnd.setOnClickListener {
                if (timePicker == null && datePicker == null) {
                    toast = Toast.makeText(
                        this@MainActivity,
                        "Задайте параметры для отмены будильника!",
                        Toast.LENGTH_LONG
                    )
                    toast!!.show()
                }
                if(timePicker == null) {
                    toast = Toast.makeText(this@MainActivity, "Выберите время для отмены будильника!", Toast.LENGTH_LONG)
                    toast!!.show()
            } else {
                    cancelAlarm()
                }
        }
    }
}

private fun showTimePicker() {
    timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(CLOCK_24H)
        .build()
    timePicker?.show(supportFragmentManager, "Time")

    timePicker?.addOnPositiveButtonClickListener {
        if (calendar == null) {
            calendar = Calendar.getInstance()
        }
        if (calendar != null) {
            calendar!!.set(Calendar.HOUR_OF_DAY, timePicker!!.hour)
            calendar!!.set(Calendar.MINUTE, timePicker!!.minute)
            calendar!!.set(Calendar.SECOND, 0)
            calendar!!.set(Calendar.MILLISECOND, 0)
        }

        sharedPreferences?.edit()?.putLong("AlarmTime", calendar!!.timeInMillis)?.apply()
        sharedPreferences?.edit()?.putInt("HOUR_OF_DAY", timePicker!!.hour)?.apply()
        sharedPreferences?.edit()?.putInt("MINUTE", timePicker!!.minute)?.apply()

        binding!!.tvTime.text = "Выбранное время: " + String.format(
            "%02d",
            (timePicker?.hour)
        ) + " : " + String.format("%02d", timePicker?.minute)
    }

    timePicker?.addOnNegativeButtonClickListener {
        timePicker?.dismiss()
    }
}

private fun showDatePicker() {
    datePicker = MaterialDatePicker.Builder.datePicker().build()
    datePicker?.show(supportFragmentManager, "Date")

    datePicker?.addOnPositiveButtonClickListener {
        binding?.tvDate?.text = "Выбранная дата: ${datePicker!!.headerText}"
        val date: List<String> = datePicker?.headerText.toString().split(", ")
        val day: String = date.toString().split(" ")[1].split(",")[0]
        val month: Int = getMonth(date[0].split(" ")[0])

        if (calendar == null) {
            calendar = Calendar.getInstance()
        }
        if (calendar != null) {
            calendar!!.set(Calendar.YEAR, date[1].toInt())
            calendar!!.set(Calendar.DAY_OF_MONTH, day.toInt())
            calendar!!.set(Calendar.MONTH, month)
        }

        sharedPreferences?.edit()?.putLong("AlarmTime", calendar!!.timeInMillis)?.apply()
        sharedPreferences?.edit()?.putInt("YEAR", date[1].toInt())?.apply()
        sharedPreferences?.edit()?.putInt("DAY_OF_MONTH", day.toInt())?.apply()
        sharedPreferences?.edit()?.putInt("MONTH", month)?.apply()
    }

    datePicker?.addOnNegativeButtonClickListener {
        datePicker?.dismiss()
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
            position = i
        }
    }
    return position
}

private fun cancelAlarm() {
    val intent = Intent(this, AlarmReceiver::class.java)

    pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

    if (alarmManager == null) {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    alarmManager?.cancel(pendingIntent)
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

