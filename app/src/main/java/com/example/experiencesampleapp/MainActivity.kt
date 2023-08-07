package com.example.experiencesampleapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.experiencesampleapp.ui.page.MainFramework
import com.example.experiencesampleapp.ui.theme.ExperienceSampleAppTheme
import com.example.experiencesampleapp.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testViewModel: TestViewModel by viewModels()
        setContent {
            ExperienceSampleAppTheme {
                //WorkerControlButton(testViewModel, this.application)
                MainFramework(testViewModel, this.application)
            }
        }
    }
}
