package com.example.experiencesampleapp.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.experiencesampleapp.MainActivity
import com.example.experiencesampleapp.R
import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.entity.Record
import com.example.experiencesampleapp.repository.CurrentWorkerRepository
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import com.example.experiencesampleapp.repository.QuestionRepository
import com.example.experiencesampleapp.repository.RecordRepository
import com.example.experiencesampleapp.repository.UserSettingRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainService : Service() {
    @Inject
    lateinit var phishingMessageRepository: PhishingMessageRepository

    @Inject
    lateinit var currentWorkerRepository: CurrentWorkerRepository

    @Inject
    lateinit var recordRepository: RecordRepository

    @Inject
    lateinit var questionRepository: QuestionRepository

    @Inject
    lateinit var userSettingRepository: UserSettingRepository

    //private val workManager = WorkManager.getInstance(this)

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onCreate()
        serviceScope.launch {
            val workerNumber = intent?.getStringExtra("worker_number") ?: "no number"
            var message: PhishingMessage
            var messageList = phishingMessageRepository.getPhishingMessageNotUsed()
            message = if (messageList.isNullOrEmpty()) {
                PhishingMessage(-1, "No data", 1)
            } else {
                //messageList[0]
                phishingMessageRepository.getRandomPhishingMessage()!!
            }
            startForeground(
                Random.nextInt(),
                makeNotification(workerNumber, message.message)
            )
            message.usedFlag = 1
            phishingMessageRepository.updatePhishingMessage(message)

            recordRepository.insertRecord(
                Record(
                    message = message.message,
                    send_time = System.currentTimeMillis(),
                    question = questionRepository.getRandomQuestion()?.question ?: "No Question"
                )
            )

            if(workerNumber == "last") {
                userSettingRepository.setIsTestFinish(true)
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

    @SuppressLint("MissingPermission", "UnspecifiedImmutableFlag")
    fun makeNotification(title: String, text: String): Notification {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent: PendingIntent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        //create a notification
        val notificationBuilder = NotificationCompat.Builder(application.applicationContext, "1")
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    application.applicationContext.resources,
                    R.drawable.messages_icon
                )
            )
            .setSmallIcon(R.drawable.messages_icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        //show the notification
//        NotificationManagerCompat.from(application.applicationContext).notify(Random.nextInt(), notificationBuilder.build())
        return notificationBuilder.build()
    }

}