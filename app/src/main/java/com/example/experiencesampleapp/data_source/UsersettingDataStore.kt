package com.example.experiencesampleapp.data_source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DateStoreManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    companion object{
        val startHour = intPreferencesKey(name = "startHour")
        val startMinute = intPreferencesKey(name = "startMinute")
        val endHour = intPreferencesKey(name = "endHour")
        val endMinute = intPreferencesKey(name = "endMinute")
        val frequency = intPreferencesKey(name = "frequency")
        val duration = intPreferencesKey(name = "duration")
        val pattern = intPreferencesKey(name = "pattern")
        val isTestStart = booleanPreferencesKey(name = "isTestStart")
        val isTestFinish = booleanPreferencesKey(name = "isTestFinish")
        val isDataExport = booleanPreferencesKey(name = "isDataExport")
        val fileName = stringPreferencesKey(name = "fileName")

    }

    suspend fun setStartHour(hour: Int) {
        dataStore.edit { pref ->
            pref[startHour] = hour
        }
    }

    suspend fun setStartMinute(minute: Int) {
        dataStore.edit { pref ->
            pref[startMinute] = minute
        }
    }

    suspend fun setEndHour(hour: Int) {
        dataStore.edit { pref ->
            pref[endHour] = hour
        }
    }

    suspend fun setEndMinute(minute: Int) {
        dataStore.edit { pref ->
            pref[endMinute] = minute
        }
    }

    suspend fun setFrequency(value: Int) {
        dataStore.edit { pref ->
            pref[frequency] = value
        }
    }

    suspend fun setDuration(value: Int) {
        dataStore.edit { pref ->
            pref[duration] = value
        }
    }

    suspend fun setPattern(value: Int) {
        dataStore.edit { pref ->
            pref[pattern] = value
        }
    }

    suspend fun setIsTestStart(value: Boolean) {
        dataStore.edit { pref ->
            pref[isTestStart] = value
        }
    }

    suspend fun setIsTestFinish(value: Boolean) {
        dataStore.edit { pref ->
            pref[isTestFinish] = value
        }
    }

    suspend fun setIsDataExport(value: Boolean) {
        dataStore.edit { pref ->
            pref[isDataExport] = value
        }
    }

    suspend fun setFileName(value: String) {
        dataStore.edit { pref ->
            pref[fileName] = value
        }
    }

    fun getStartHour(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val timePoint = pref[startHour] ?:10
                timePoint
            }
    }

    fun getStartMinute(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val timePoint = pref[startMinute] ?:0
                timePoint
            }
    }

        fun getEndHour(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val timePoint = pref[endHour] ?:22
                timePoint
            }
    }

    fun getEndMinute(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val timePoint = pref[endMinute] ?:0
                timePoint
            }
    }

    fun getFrequency(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[frequency] ?:6
                value
            }
    }

    fun getDuration(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[duration] ?:14
                value
            }
    }

    fun getPattern(): Flow<Int> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[pattern] ?:1
                value
            }
    }

    fun getIsTestStart(): Flow<Boolean> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[isTestStart] ?:false
                value
            }
    }

    fun getIsTestFinish(): Flow<Boolean> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[isTestFinish] ?:false
                value
            }
    }

    fun getIsDataExport(): Flow<Boolean> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[isDataExport] ?:false
                value
            }
    }

    fun getFileName(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { pref ->
                val value = pref[fileName] ?: "null"
                value
            }
    }
}