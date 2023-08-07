package com.example.experiencesampleapp.ui.page

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.experiencesampleapp.ui.componments.MessageBox
import com.example.experiencesampleapp.viewmodel.TestViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    testViewModel: TestViewModel,
) {

    val recordListState = testViewModel.getAllRecordsAsFlow().collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isTestFinish = testViewModel.getIsTestFinish().collectAsState(initial = false)
    val openDialog = remember { mutableStateOf(!testViewModel.isDialogOpen) }

    //Text(text ="duration: ${duration.value} frequency: ${frequency.value}")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        coroutineScope.launch {
            if (recordListState.value.size >= 2) {
                lazyListState.scrollToItem(recordListState.value.size - 1)
            }
        }
        items(recordListState.value.size) { index ->
            val record = recordListState.value[index]
            MessageBox(viewModel = testViewModel, record = record)
        }
    }

    if (openDialog.value && isTestFinish.value) {
        LastDialog(
            onDismiss = {
                openDialog.value = false
                testViewModel.isDialogOpen = true
            },
            toDo = {
                openDialog.value = false
                testViewModel.isDialogOpen = true
            }
        )
    }

}

@Composable
fun LastDialog(
    onDismiss: () -> Unit,
    toDo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(
                text = "Congratulations!",
                fontWeight = FontWeight.W700,
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Text(
                text = "You have received the last notification!" +
                        "\nAfter answering the question, please export data on the settings page." +
                        "\nThere are two ways to send data to me:" +
                        "\n1. Send me via email: xuwen.luo.2022@uni.strath.ac.uk" +
                        "\n2. Send me via the link in previous email.",
                fontSize = 15.sp
            )
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { toDo() }
                ) {
                    Text("Ok")
                }
            }
        }
    )
}

