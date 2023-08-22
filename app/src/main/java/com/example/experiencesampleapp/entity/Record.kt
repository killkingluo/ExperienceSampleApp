package com.example.experiencesampleapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val send_time: Long,
    var respond_type: Int = 0,
    var respond_time: Long = 0,
    val question: String = "Null",
    var answers: Int = 0,
    var answer_time: Long = 0,
)