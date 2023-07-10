package com.example.experiencesampleapp.entity

import androidx.annotation.StringRes
import com.example.experiencesampleapp.R

//navigation model
sealed class Page(val route: String, @StringRes val resourceId: Int) {
    object Home : Page("Home", R.string.Home)
    object Settings : Page("Settings", R.string.Settings)
}