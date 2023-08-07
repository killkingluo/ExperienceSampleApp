package com.example.experiencesampleapp.ui.page

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.experiencesampleapp.service.MainService
import com.example.experiencesampleapp.ui.componments.PermissionRequestButton
import com.example.experiencesampleapp.viewmodel.TestViewModel

@SuppressLint("NewApi", "FlowOperatorInvokedInComposition")
@Composable
fun SettingPage(
    testViewModel: TestViewModel,
    application: Application,
) {

    val hourList = (0..23).toList()
    val minuteList = (0..59).toList()

    val startHour = testViewModel.getStartHour().collectAsState(initial = 10)
    val startMinute = testViewModel.getStartMinute().collectAsState(initial = 0)
    val endHour = testViewModel.getEndHour().collectAsState(initial = 22)
    val endMinute = testViewModel.getEndMinute().collectAsState(initial = 0)
    val frequency = testViewModel.getFrequency().collectAsState(initial = 6)
    val duration = testViewModel.getDuration().collectAsState(initial = 7)
    val pattern = testViewModel.getPattern().collectAsState(initial = 1)
    val isTestStarted = testViewModel.getIsTestStart().collectAsState(initial = false)
    val isDataExport = testViewModel.getIsDataExport().collectAsState(initial = false)
    val fileName = testViewModel.getFileName().collectAsState(initial = "null")

    val currentStartHour = remember { mutableStateOf(startHour.value) }
    val currentStartMinute = remember { mutableStateOf(startMinute.value) }
    val currentEndHour = remember { mutableStateOf(endHour.value) }
    val currentEndMinute = remember { mutableStateOf(endMinute.value) }
    val currentFrequency = remember { mutableStateOf(frequency.value) }
    val currentDuration = remember { mutableStateOf(duration.value) }
    val currentPattern = remember { mutableStateOf(pattern.value) }

    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PermissionRequestButton(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            name = "Notification"
        )
        PermissionRequestButton(
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE,
            name = "WriteFile"
        )

//        Button(
//            onClick = {
//                testViewModel.insertPhishingMessage()
//                testViewModel.insertQuestion()
//            }
//        ) {
//            Text(text = "Insert test data")
//        }

        Text("Research Settings")

        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(35.dp),
        ) {
            Text(text = "Start Time: ")
            Text(text = "${startHour.value}:${startMinute.value}")
            Spacer(Modifier.weight(1f))
            currentStartHour.value = dropdownMenu(hourList, startHour.value)
            Text(":")
            currentStartMinute.value = dropdownMenu(minuteList, startMinute.value)
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(35.dp),
        ) {
            Text(text = "End Time: ")
            Text(text = "${endHour.value}:${endMinute.value}")
            Spacer(Modifier.weight(1f))
            currentEndHour.value = dropdownMenu(hourList, endHour.value)
            Text(":")
            currentEndMinute.value = dropdownMenu(minuteList, endMinute.value)
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(35.dp),
        ) {
            Text(text = "Frequency: ")
            Text(text = "${frequency.value}")
            Spacer(Modifier.weight(1f))
            currentFrequency.value = dropdownMenu((1..20).toList(), frequency.value)
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(35.dp),
        ) {
            Text(text = "Duration: ")
            Text(text = "${duration.value}")
            Spacer(Modifier.weight(1f))
            currentDuration.value = dropdownMenu((1..14).toList(), duration.value)
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(35.dp),
        ) {
            Text(text = "Pattern: ")
            Text(text = "${pattern.value}")
            Spacer(Modifier.weight(1f))
            currentPattern.value = dropdownMenu((1..3).toList(), pattern.value)
        }

        Button(
            enabled = !isTestStarted.value,
            onClick = {
                testViewModel.setStartHour(currentStartHour.value)
                testViewModel.setStartMinute(currentStartMinute.value)
                testViewModel.setEndHour(currentEndHour.value)
                testViewModel.setEndMinute(currentEndMinute.value)
                testViewModel.setFrequency(currentFrequency.value)
                testViewModel.setDuration(currentDuration.value)
                testViewModel.setPattern(currentPattern.value)
            }
        ) {
            Text(text = "Save Settings")
        }

        Button(
            enabled = !isTestStarted.value,
            onClick = {
                testViewModel.insertPhishingMessage()
                testViewModel.insertQuestion()
                when (pattern.value) {
                    1 -> testViewModel.messageSendFixedSamplingScheme(
                        startHour = startHour.value,
                        startMinute = startMinute.value,
                        endHour = endHour.value,
                        endMinute = endMinute.value,
                        frequency = frequency.value,
                        durationDays = duration.value,
                    )

                    2 -> testViewModel.messageSendRandomSamplingScheme(
                        startHour = startHour.value,
                        startMinute = startMinute.value,
                        endHour = endHour.value,
                        endMinute = endMinute.value,
                        frequency = frequency.value,
                        durationDays = duration.value,
                    )

                    else -> testViewModel.messageSendSemiRandomSamplingScheme(
                        startHour = startHour.value,
                        startMinute = startMinute.value,
                        endHour = endHour.value,
                        endMinute = endMinute.value,
                        frequency = frequency.value,
                        durationDays = duration.value,
                    )
                }
                testViewModel.setIsTestStart(true)
            }
        ) {
            Text(text = "Start Test")
        }

        Button(
            enabled = isTestStarted.value,
            onClick = { openDialog.value = true }
        ) {
            Text(text = "Stop Test")
            if (openDialog.value) {
                YesOrNoAlertDialog(
                    title = "Stop Test Warning",
                    alertContent = "Are you sure you want to stop the test?",
                    onDismiss = { openDialog.value = false },
                    toDO = {
                        Intent(application.applicationContext, MainService::class.java).apply {
                            application.stopService(this)
                        }
                        testViewModel.cancelAllWorker()
                        testViewModel.deleteAllData()
                        testViewModel.setIsTestStart(false)
                        openDialog.value = false
                    }
                )
            }
        }

        Button(
            onClick = {
                testViewModel.exportAllRecordsToCSV()
                testViewModel.setIsDataExport(true)
            }
        ) {
            Text(text = "Export data")
        }
        if (isDataExport.value) {
            Text(text = "Exported to /${Environment.DIRECTORY_DOCUMENTS}/${fileName.value}")
        }
    }
}

@Composable
fun dropdownMenu(dropDownList: List<Int>, value: Int): Int {
    val expanded = remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(value) }
    TextButton(
        onClick = { expanded.value = true },
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxHeight()
    ) {
        Text(text = currentValue.value.toString())
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .size(20.dp)
        )
    }
    DropdownMenu(
        modifier = Modifier.fillMaxHeight(),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        for (item in dropDownList) {
            DropdownMenuItem(
                modifier = Modifier.fillMaxHeight(),
                onClick = {
                    expanded.value = false
                    currentValue.value = item
                }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = item.toString(),
                )
            }
        }
    }
    return currentValue.value
}

@Composable
fun YesOrNoAlertDialog(
    title: String,
    alertContent: String,
    onDismiss: () -> Unit,
    toDO: () -> Unit
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = alertContent) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = toDO
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("No")
            }
        }
    )
}
