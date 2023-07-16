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
import com.example.experiencesampleapp.entity.TimePoint
import com.example.experiencesampleapp.function.InsertTestData
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
        Button(
            onClick = {
                testViewModel.insertPhishingMessage()
            }
        ) {
            Text(text = "Insert test data")
        }

        Text("Test Notification")
        Button(
            onClick = {

//                testViewModel.messageSendWorker(16, 47)
//                testViewModel.messageSendFixedSamplingScheme(
//                    DailyStartTime = TimePoint(21, 0),
//                    DailyEndTime = TimePoint(22, 0),
//                    frequency = 10,
//                    durationDays = 7,
//                    pattern = 1
//                )
                //testViewModel.messageSendRandomSamplingScheme(TimePoint(9), TimePoint(22),10,7,1)

                testViewModel.messageSendSemiRandomSamplingScheme(
                    TimePoint(9),
                    TimePoint(22),
                    10,
                    7,
                    1
                )
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