package com.example.experiencesampleapp.data_source

import androidx.room.*
import com.example.experiencesampleapp.entity.CurrentWorker
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWorkerDao {
    //get all phishing message
    @Query("SELECT * FROM current_worker ORDER BY id ASC")
    fun getAllWorkers(): Flow<List<CurrentWorker>>

    //get phishing message by id
    @Query("SELECT * FROM current_worker WHERE id = :id LIMIT 1")
    fun getCurrentWorkerById(id: Int): CurrentWorker?

    //get phishing message by id as flow
    @Query("SELECT * FROM current_worker WHERE id = :id LIMIT 1")
    fun getCurrentWorkerByIdAsFlow(id: Int): Flow<CurrentWorker>?

    @Query("UPDATE current_worker SET workerState = :workerState WHERE workerName = :workerName")
    fun updateCurrentWorkerByName(workerState:Int, workerName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWorker(currentWorker: CurrentWorker)

    @Update
    suspend fun updateCurrentWorker(currentWorker: CurrentWorker)

    @Delete
    suspend fun deleteCurrentWorker(currentWorker: CurrentWorker)

    @Query("DELETE FROM current_worker")
    suspend fun deleteAllData()
}