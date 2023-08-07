package com.example.experiencesampleapp.repository


import com.example.experiencesampleapp.data_source.DateStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingRepository @Inject constructor(private val dateStoreManager: DateStoreManager) {

    suspend fun setStartHour(hour: Int) {
        dateStoreManager.setStartHour(hour)
    }

    suspend fun setStartMinute(minute: Int) {
        dateStoreManager.setStartMinute(minute)
    }

    suspend fun setEndHour(hour: Int) {
        dateStoreManager.setEndHour(hour)
    }

    suspend fun setEndMinute(minute: Int) {
        dateStoreManager.setEndMinute(minute)
    }

    suspend fun setFrequency(value: Int) {
        dateStoreManager.setFrequency(value)
    }

    suspend fun setDuration(value: Int) {
        dateStoreManager.setDuration(value)
    }

    suspend fun setPattern(value: Int) {
        dateStoreManager.setPattern(value)
    }

    suspend fun setIsTestStart(value: Boolean) {
        dateStoreManager.setIsTestStart(value)
    }

    suspend fun setIsTestFinish(value: Boolean) {
        dateStoreManager.setIsTestFinish(value)
    }

    suspend fun setIsDataExport(value: Boolean) {
        dateStoreManager.setIsDataExport(value)
    }

    suspend fun setFileName(value: String) {
        dateStoreManager.setFileName(value)
    }

    fun getStartHour(): Flow<Int> {
        return dateStoreManager.getStartHour()
    }

    fun getStartMinute(): Flow<Int> {
        return dateStoreManager.getStartMinute()
    }

    fun getEndHour(): Flow<Int> {
        return dateStoreManager.getEndHour()
    }

    fun getEndMinute(): Flow<Int> {
        return dateStoreManager.getEndMinute()
    }

    fun getFrequency(): Flow<Int> {
        return dateStoreManager.getFrequency()
    }

    fun getDuration(): Flow<Int> {
        return dateStoreManager.getDuration()
    }

    fun getPattern(): Flow<Int> {
        return dateStoreManager.getPattern()
    }

    fun getIsTestStart(): Flow<Boolean> {
        return dateStoreManager.getIsTestStart()
    }

    fun getIsTestFinish(): Flow<Boolean> {
        return dateStoreManager.getIsTestFinish()
    }

    fun getIsDataExport(): Flow<Boolean> {
        return dateStoreManager.getIsDataExport()
    }

    fun getFileName(): Flow<String> {
        return dateStoreManager.getFileName()
    }
}