package com.example.experiencesampleapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val send_time: Long,
    val respond_type: Int,
    val respond_time: Long,
    val question: String,
    val answers: Int
)