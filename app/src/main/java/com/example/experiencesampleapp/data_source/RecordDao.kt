package com.example.experiencesampleapp.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.entity.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    //get all records
    @Query("SELECT * FROM records ORDER BY id ASC")
    fun getAllRecords(): Flow<List<Record>>

    //get record by id
    @Query("SELECT * FROM records WHERE id = :id LIMIT 1")
    fun getRecordById(id: Int): Record?

    //get record by id as flow
    @Query("SELECT * FROM records WHERE id = :id LIMIT 1")
    fun getRecordByIdAsFlow(id: Int): Flow<Record>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)
}