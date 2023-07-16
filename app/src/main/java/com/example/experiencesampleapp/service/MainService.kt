package com.example.experiencesampleapp.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.experiencesampleapp.R
import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.repository.CurrentWorkerRepository
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainService : Service() {
    @Inject
    lateinit var phishingMessageRepository: PhishingMessageRepository

    @Inject
    lateinit var currentWorkerRepository: CurrentWorkerRepository

    private val workManager = WorkManager.getInstance(this)

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onCreate()
        serviceScope.launch {
            val message: PhishingMessage
            val messageList = phishingMessageRepository.getPhishingMessageNotUsed()
            message = if (messageList == null) {
                PhishingMessage(-1, "No data", 1)
            } else {
                messageList[0]
            }
            startForeground(
                Random.nextInt(),
                makeNotification(message.message, message.message)
            )
            message.usedFlag = 1
            phishingMessageRepository.updatePhishingMessage(message)

            for(i in 1..200) {
                withContext(Dispatchers.IO) {
                    workManager.getWorkInfosForUniqueWork("everyDayMessageSendTrigger$i").get()
                }.forEach {
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        currentWorkerRepository.updateCurrentWorkerByName(1, "everyDayMessageSendTrigger$i")
                    }
                }
            }
            stopForeground(false)
        }
        //show the notification
//        NotificationManagerCompat.from(application.applicationContext).notify(
//            Random.nextInt(),
//            makeNotification(message?.message ?: "No data", message?.message ?: "No data")
//        )
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(false)
    }

    @SuppressLint("MissingPermission")
    fun makeNotification(title: String, text: String): Notification {
        //create a notification
        val notificationBuilder = NotificationCompat.Builder(application.applicationContext, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        //show the notification
//        NotificationManagerCompat.from(application.applicationContext).notify(Random.nextInt(), notificationBuilder.build())
        return notificationBuilder.build()
    }

}