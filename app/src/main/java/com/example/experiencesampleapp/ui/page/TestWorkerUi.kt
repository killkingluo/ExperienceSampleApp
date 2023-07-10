package com.example.experiencesampleapp.ui.page

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.entity.TimePoint
import com.example.experiencesampleapp.service.MainService
import com.example.experiencesampleapp.ui.componments.PermissionRequestButton
import com.example.experiencesampleapp.viewmodel.TestViewModel

@SuppressLint("NewApi")
@Composable
fun WorkerControlButton(
    testViewModel: TestViewModel,
    application: Application,
    navController: NavController,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PermissionRequestButton(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            name = "Notification"
        )

        Text("Test Notification")
        Button(
            onClick = {
                for (i in 1..10) {
                    testViewModel.insertPhishingMessage(
                        PhishingMessage(
                            message = "Test Notification $i",
                            usedFlag = 0
                        )
                    )
                }
                //testViewModel.messageSendWorker(18, 12)
                testViewModel.messageSendFixedSamplingScheme(
                    DailyStartTime = TimePoint(9, 0),
                    DailyEndTime = TimePoint(22, 0),
                    frequency = 10,
                    durationDays = 7,
                    pattern = 1
                )
                //testViewModel.messageSendRandomSamplingScheme(TimePoint(9), TimePoint(22),10,7,1)
            }
        ) {
            Text(text = "Start Service")
        }
        Button(
            onClick = {
                Intent(application.applicationContext, MainService::class.java).apply {
                    application.stopService(this)
                }
                testViewModel.cancelMessageSendWorker()
            }
        ) {
            Text(text = "Stop Service")
        }
    }
}