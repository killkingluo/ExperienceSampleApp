package com.example.experiencesampleapp.workers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.experiencesampleapp.R
import com.example.experiencesampleapp.service.MainService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

@HiltWorker
class SendMessageWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    @SuppressLint("SuspiciousIndentation")
    override fun doWork(): Result {

//        val packageName = "com.example.experiencesampleapp"
//        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        val workerNumber = inputData.getString("worker_number")

        val serviceIntent = Intent(applicationContext, MainService::class.java)
        serviceIntent.putExtra("worker_number", workerNumber)
        ContextCompat.startForegroundService(context, serviceIntent)
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    fun makeNotification(title: String, text: String) {
        //create a notification
        val notificationBuilder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.messages_icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        //show the notification
        NotificationManagerCompat.from(context)
            .notify(Random.nextInt(), notificationBuilder.build())
    }
}