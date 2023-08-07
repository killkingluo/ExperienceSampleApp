package com.example.experiencesampleapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String
)
