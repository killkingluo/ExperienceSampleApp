package com.example.experiencesampleapp.repository

import com.example.experiencesampleapp.data_source.RecordDao
import com.example.experiencesampleapp.entity.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository@Inject constructor(private val recordDao: RecordDao) {
    //get all records
    fun getAllRecords(): Flow<List<Record>> = recordDao.getAllRecords()

    //get record by id
    fun getRecordById(id: Int): Record? = recordDao.getRecordById(id)

    //get record by id as flow
    fun getRecordByIdAsFlow(id: Int): Flow<Record>? = recordDao.getRecordByIdAsFlow(id)

    suspend fun insertRecord(record: Record) = recordDao.insertRecord(record)

    suspend fun updateRecord(record: Record) = recordDao.updateRecord(record)

    suspend fun deleteRecord(record: Record) = recordDao.deleteRecord(record)
}