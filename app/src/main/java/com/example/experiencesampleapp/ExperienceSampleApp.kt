package com.example.experiencesampleapp

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.experiencesampleapp.broadcast.PowerModeReceiver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ExperienceSampleApp : Application(), Configuration.Provider {

    @Inject lateinit var hiltWorkerFactory: HiltWorkerFactory
    private lateinit var powerModeReceiver: PowerModeReceiver
    @SuppressLint("WrongConstant")
    override fun onCreate() {
        super.onCreate()
        instance = this
        //create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                "encourage channel",
                NotificationManager.IMPORTANCE_MAX
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onTerminate() {
        unregisterReceiver(powerModeReceiver)
        super.onTerminate()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()

    companion object {
        lateinit var instance: ExperienceSampleApp
    }

}