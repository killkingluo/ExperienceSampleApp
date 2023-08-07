package com.example.experiencesampleapp.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.experiencesampleapp.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    //get all questions as flow
    @Query("SELECT * FROM question_table ORDER BY id ASC")
    fun getAllQuestionsAsFlow(): Flow<List<Question>>

    //get a random question
    @Query("SELECT * FROM question_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuestion(): Question?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("DELETE FROM question_table")
    suspend fun deleteAllData()

}