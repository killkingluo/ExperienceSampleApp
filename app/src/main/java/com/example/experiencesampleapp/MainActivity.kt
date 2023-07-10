package com.example.experiencesampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.experiencesampleapp.ui.page.MainFramework
import com.example.experiencesampleapp.ui.theme.ExperienceSampleAppTheme
import com.example.experiencesampleapp.viewmodel.TestViewModel
import com.example.experiencesampleapp.ui.page.WorkerControlButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
