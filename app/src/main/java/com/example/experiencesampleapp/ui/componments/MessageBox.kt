package com.example.experiencesampleapp.ui.componments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.experiencesampleapp.R
import com.example.experiencesampleapp.entity.Record
import com.example.experiencesampleapp.function.timestampToDate
import com.example.experiencesampleapp.viewmodel.TestViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageBox(viewModel: TestViewModel, record: Record) {
    val currentRespondType  = remember { mutableStateOf(record.respond_type) }
    val currentAnswers = remember { mutableStateOf(record.answers) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp),
            color = Color.Gray,
            text = timestampToDate(record.send_time),
            style = MaterialTheme.typography.body2
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp) // 外边距
                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp))),
            backgroundColor = colorResource(R.color.light_pink) // 设置背景色

        ) {
            Column(
                modifier = Modifier.padding(5.dp) // 内边距
            ) {
                Text(
                    record.message
                )

                Text(
                    style = MaterialTheme.typography.h6,
                    color = colorResource(R.color.gray_blue),
                    text = "\nYour reaction:"
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = {
                        currentRespondType.value = 1
                        record.respond_type = 1
                        record.respond_time = System.currentTimeMillis()
                        viewModel.updateRecord(record)
                    }
                ) {
                    if (currentRespondType.value == 1) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Text("Open link")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = {
                        currentRespondType.value = 2
                        record.respond_type = 2
                        record.respond_time = System.currentTimeMillis()
                        viewModel.updateRecord(record)
                    }
                ) {
                    if (currentRespondType.value == 2) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Text("Enquire")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = {
                        currentRespondType.value = 3
                        record.respond_type = 3
                        record.respond_time = System.currentTimeMillis()
                        viewModel.updateRecord(record)
                    }
                ) {
                    if (currentRespondType.value == 3) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Text("Dismiss")
                }

                Text(
                    style = MaterialTheme.typography.h6,
                    color = colorResource(R.color.gray_blue),
                    text = "\nFollow question:"
                )
                Text(
                    record.question
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = {
                        currentAnswers.value = 1
                        record.answers = 1
                        viewModel.updateRecord(record)
                    }
                ) {
                    if (currentAnswers.value == 1) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Text("Yes")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = {
                        currentAnswers.value = 2
                        record.answers = 2
                        viewModel.updateRecord(record)
                    }
                ) {
                    if (currentAnswers.value == 2) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Text("No")
                }
            }
        }
    }
}