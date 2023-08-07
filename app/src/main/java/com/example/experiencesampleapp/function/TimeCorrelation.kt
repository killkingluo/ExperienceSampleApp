package com.example.experiencesampleapp.function

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*

//calculate the time difference between now and the time to start the worker
fun timeTransform(hour: Int, minute: Int): Long {
    val calendar: Calendar = Calendar.getInstance()
    val nowMillis: Long = calendar.timeInMillis

    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    if (calendar.before(Calendar.getInstance())) {
        calendar.add(Calendar.DATE, 1)
    }

    Log.d("timeTransform", "timeTransform: ${calendar.timeInMillis - nowMillis}")
    return calendar.timeInMillis - nowMillis
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun timestampToDate(timestamp: Long): String {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is zero-based
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    //return calendar.time.toString()
    return String.format("%04d %02d.%02d %02d:%02d", year, month, day, hour, minute)
}