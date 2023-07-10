package com.example.experiencesampleapp.function

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

fun getTodayTimestamp(): Long {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date().time
        )
    )!!.time
}

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

//calculate the time difference between now and the time to start the worker
fun timeTransformV2(hour: Int, minute: Int): Long {
    val calendar: Calendar = Calendar.getInstance()

    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    if (calendar.before(Calendar.getInstance())) {
        calendar.add(Calendar.DATE, 1)
    }

    Log.d("timeTransform", "timeTransform: ${calendar.timeInMillis}")
    return calendar.timeInMillis
}

@RequiresApi(Build.VERSION_CODES.O)
fun timestampToDate(timestamp: Long): String {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    return calendar.time.toString()
}