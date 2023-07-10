package com.example.experiencesampleapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.experiencesampleapp.R
import com.example.experiencesampleapp.entity.CurrentWorker
import com.example.experiencesampleapp.entity.TimePoint
import com.example.experiencesampleapp.function.timeTransform
import com.example.experiencesampleapp.function.timestampToDate
import com.example.experiencesampleapp.repository.CurrentWorkerRepository
import com.example.experiencesampleapp.workers.SendMessageWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

interface TestViewModelAbstract {

}

@HiltViewModel
class TestViewModel @Inject constructor(
    private val phishingMessageRepository: PhishingMessageRepository,
    private val currentWorkerRepository: CurrentWorkerRepository,
    private val application: Application,
) : ViewModel(), TestViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    //create a workManager
    private val workManager = WorkManager.getInstance(application.applicationContext)

    fun insertPhishingMessage(phishingMessage: PhishingMessage) {
        ioScope.launch {
            phishingMessageRepository.insertPhishingMessage(phishingMessage)
        }
    }

    fun insertCurrentWorker(currentWorker: CurrentWorker) {
        ioScope.launch {
            currentWorkerRepository.insertCurrentWorker(currentWorker)
        }
    }

    @SuppressLint("MissingPermission")
    fun makeNotification(title: String, text: String) {
        //create a notification
        val notificationBuilder = NotificationCompat.Builder(application.applicationContext, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        //show the notification
        NotificationManagerCompat.from(application.applicationContext)
            .notify(Random.nextInt(), notificationBuilder.build())
    }

    fun messageSendWorker(hour: Int, minute: Int) {

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val periodWorkRequest = PeriodicWorkRequestBuilder<SendMessageWorker>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .setInitialDelay(timeTransform(hour, minute), TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "everyDayMessageSendTrigger",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodWorkRequest
        )
        calendar.add(Calendar.DATE, 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun messageSendFixedSamplingScheme(
        DailyStartTime: TimePoint,
        DailyEndTime: TimePoint,
        frequency: Int,
        durationDays: Int,
        pattern: Int
    ) {

        val calendar = Calendar.getInstance()
        val nowMillis: Long = calendar.timeInMillis
        var startTimePoint: Long = 0
        var endTimePoint: Long = 0
        var count = 0

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, DailyStartTime.hour)
        calendar.set(Calendar.MINUTE, DailyStartTime.minute)
        startTimePoint = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, DailyEndTime.hour)
        calendar.set(Calendar.MINUTE, DailyEndTime.minute)
        endTimePoint = calendar.timeInMillis
        val gap = (endTimePoint - startTimePoint) / (frequency - 1)

        for (i in 1..durationDays) {
            startTimePoint += 86400000
            endTimePoint += 86400000
            for (j in startTimePoint..endTimePoint step gap) {
                val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SendMessageWorker>()
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                    )
                    .setInitialDelay(j - nowMillis, TimeUnit.MILLISECONDS)
                    .build()
                count++
                //Log.d("time $count", timestampToDate(j))
                workManager.enqueueUniqueWork(
                    "everyDayMessageSendTrigger$count",
                    ExistingWorkPolicy.REPLACE,
                    oneTimeWorkRequest
                )

                insertCurrentWorker(
                    CurrentWorker(
                        id = count,
                        workerName = "everyDayMessageSendTrigger$count",
                        workerState = 0,
                        time = timestampToDate(j)
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun messageSendRandomSamplingScheme(
        DailyStartTime: TimePoint,
        DailyEndTime: TimePoint,
        frequency: Int,
        durationDays: Int,
        pattern: Int
    ) {

        val calendar = Calendar.getInstance()
        val nowMillis: Long = calendar.timeInMillis
        var startTimePoint: Long = 0
        var endTimePoint: Long = 0

        var randomTimePoint: Long

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, DailyStartTime.hour)
        calendar.set(Calendar.MINUTE, DailyStartTime.minute)
        startTimePoint = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, DailyEndTime.hour)
        calendar.set(Calendar.MINUTE, DailyEndTime.minute)
        endTimePoint = calendar.timeInMillis

        for (i in 1..durationDays) {
            for (j in 1..frequency) {
                randomTimePoint =
                    Random.nextLong(startTimePoint + i * 86400000, endTimePoint + i * 86400000)
                val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SendMessageWorker>()
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                    )
                    .setInitialDelay(randomTimePoint - nowMillis, TimeUnit.MILLISECONDS)
                    .build()
                workManager.enqueueUniqueWork(
                    "everyDayMessageSendTrigger$j",
                    ExistingWorkPolicy.REPLACE,
                    oneTimeWorkRequest
                )
                Log.d("randomTimePoint", timestampToDate(randomTimePoint))
            }
        }
    }

    fun messageSendSemiRandomSamplingScheme(
        DailyStartTime: TimePoint,
        DailyEndTime: TimePoint,
        frequency: Int,
        durationDays: Int,
        pattern: Int
    ) {

        val calendar = Calendar.getInstance()
        val nowMillis: Long = calendar.timeInMillis
        var startTimePoint: Long = 0
        var endTimePoint: Long = 0
        var count = 0

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, DailyStartTime.hour)
        calendar.set(Calendar.MINUTE, DailyStartTime.minute)
        startTimePoint = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, DailyEndTime.hour)
        calendar.set(Calendar.MINUTE, DailyEndTime.minute)
        endTimePoint = calendar.timeInMillis
        val gap = (endTimePoint - startTimePoint) / (frequency - 1)

        for (i in 0..durationDays) {
            startTimePoint += i * 86400000
            endTimePoint += i * 86400000
            for (j in startTimePoint..endTimePoint step gap) {
                val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SendMessageWorker>()
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                    )
                    .setInitialDelay(j - nowMillis + Random.nextLong(0, gap), TimeUnit.MILLISECONDS)
                    .build()
                count++
                workManager.enqueueUniqueWork(
                    "everyDayMessageSendTrigger$count",
                    ExistingWorkPolicy.REPLACE,
                    oneTimeWorkRequest
                )
            }
        }
    }

    fun cancelMessageSendWorker() {
        for (i in 1..999) {
            workManager.cancelUniqueWork("everyDayMessageSendTrigger$i")
        }
    }
}