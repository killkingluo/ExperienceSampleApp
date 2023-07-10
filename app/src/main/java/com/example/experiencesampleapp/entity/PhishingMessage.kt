package com.example.experiencesampleapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phishing_message")
data class PhishingMessage (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    var usedFlag: Int //0 never use, 1 used
)
