package com.example.experiencesampleapp.repository

import com.example.experiencesampleapp.data_source.CurrentWorkerDao
import com.example.experiencesampleapp.entity.CurrentWorker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentWorkerRepository @Inject constructor(private val currentWorkerDao: CurrentWorkerDao) {

    //get all record
    fun getAllWorkers(): Flow<List<CurrentWorker>> = currentWorkerDao.getAllWorkers()

    //get worker by id
    fun getCurrentWorkerById(id: Int):CurrentWorker? = currentWorkerDao.getCurrentWorkerById(id)

    fun getCurrentWorkerByIdAsFlow(id: Int): Flow<CurrentWorker>? = currentWorkerDao.getCurrentWorkerByIdAsFlow(id)

    suspend fun updateCurrentWorkerByName(workerState:Int, workerName: String) = currentWorkerDao.updateCurrentWorkerByName(workerState, workerName)

    suspend fun insertCurrentWorker(currentWorker: CurrentWorker) = currentWorkerDao.insertCurrentWorker(currentWorker)

    suspend fun updateCurrentWorker(currentWorker: CurrentWorker) = currentWorkerDao.updateCurrentWorker(currentWorker)

    suspend fun deleteCurrentWorker(currentWorker: CurrentWorker) = currentWorkerDao.deleteCurrentWorker(currentWorker)

    suspend fun deleteAllData() = currentWorkerDao.deleteAllData()
}