package com.example.experiencesampleapp.data_source

import androidx.room.*
import com.example.experiencesampleapp.entity.PhishingMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface PhishingMessageDao {
    //get all phishing message
    @Query("SELECT * FROM phishing_message ORDER BY id ASC")
    fun getAllPhishingMessages(): Flow<List<PhishingMessage>>

    //get phishing message by id
    @Query("SELECT * FROM phishing_message WHERE id = :id LIMIT 1")
    fun getPhishingMessageById(id: Int): PhishingMessage?

    //get phishing message by id as flow
    @Query("SELECT * FROM phishing_message WHERE id = :id LIMIT 1")
    fun getPhishingMessageByIdAsFlow(id: Int): Flow<PhishingMessage>?

    //get phishing message not used
    @Query("SELECT * FROM phishing_message WHERE usedFlag = 0 LIMIT 100")
    fun getPhishingMessageNotUsed(): List<PhishingMessage>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhishingMessage(phishingMessage: PhishingMessage)

    @Update
    suspend fun updatePhishingMessage(phishingMessage: PhishingMessage)

    @Delete
    suspend fun deletePhishingMessage(phishingMessage: PhishingMessage)
}