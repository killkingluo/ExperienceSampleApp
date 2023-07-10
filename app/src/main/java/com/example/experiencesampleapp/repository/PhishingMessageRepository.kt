package com.example.experiencesampleapp.repository

import com.example.experiencesampleapp.data_source.PhishingMessageDao
import com.example.experiencesampleapp.entity.PhishingMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhishingMessageRepository @Inject constructor(private val phishingMessageDao: PhishingMessageDao) {

    //get all record
    fun getAllPhishingMessages(): Flow<List<PhishingMessage>> = phishingMessageDao.getAllPhishingMessages()

    //get phishing message by id
    fun getPhishingMessageById(id: Int):PhishingMessage? = phishingMessageDao.getPhishingMessageById(id)

    fun getPhishingMessageByIdAsFlow(id: Int): Flow<PhishingMessage>? = phishingMessageDao.getPhishingMessageByIdAsFlow(id)

    fun getPhishingMessageNotUsed(): List<PhishingMessage>? = phishingMessageDao.getPhishingMessageNotUsed()

    suspend fun insertPhishingMessage(phishingMessage: PhishingMessage) = phishingMessageDao.insertPhishingMessage(phishingMessage)

    suspend fun updatePhishingMessage(phishingMessage: PhishingMessage) = phishingMessageDao.updatePhishingMessage(phishingMessage)

    suspend fun deletePhishingMessage(phishingMessage: PhishingMessage) = phishingMessageDao.deletePhishingMessage(phishingMessage)
}