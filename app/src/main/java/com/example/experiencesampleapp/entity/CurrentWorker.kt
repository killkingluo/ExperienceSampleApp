package com.example.experiencesampleapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_worker")
data class CurrentWorker(
    @PrimaryKey
    val id: Int = 0,
    val workerName: String,
    val workerState: Int, //0 Enqueued, 1 Succeeded, 2 Failed
    val time: String
)
