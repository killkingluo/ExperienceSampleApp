package com.example.experiencesampleapp.repository

import com.example.experiencesampleapp.data_source.QuestionDao
import com.example.experiencesampleapp.entity.Question
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionDao: QuestionDao) {

    //get all questions as flow
    fun getAllQuestionsAsFlow() = questionDao.getAllQuestionsAsFlow()

    //get a random question
    fun getRandomQuestion() = questionDao.getRandomQuestion()

    suspend fun insertQuestion(question: Question) = questionDao.insertQuestion(question)

    suspend fun updateQuestion(question: Question) = questionDao.updateQuestion(question)

    suspend fun deleteQuestion(question: Question) = questionDao.deleteQuestion(question)

    suspend fun deleteAllData() = questionDao.deleteAllData()
}