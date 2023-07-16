package com.example.experiencesampleapp.ui.componments

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.experiencesampleapp.R

@Composable
fun MessageBox(time: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp),
            color = Color.Gray,
            text = time,
            style = MaterialTheme.typography.body2
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp) // 外边距
                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                .clickable { },
            backgroundColor = colorResource(R.color.light_pink) // 设置背景色

        ) {
            Column(
                modifier = Modifier.padding(5.dp) // 内边距
            ) {
                Text(
                    "Hello, I am an Amazon company promoter and I am participating in my exclusive event to enjoy a 50% discount~\n" +
                            "Click on the link below to view:\n" +
                            "https://www.annazon.co.uk/discount/56392"
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
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text("Open link")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text("Enquire")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.baby_blue),
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text("Dismiss")
                }

                Text(
                    style = MaterialTheme.typography.h6,
                    color = colorResource(R.color.gray_blue),
                    text = "\nFollow question:"
                )
            }
        }
    }
}