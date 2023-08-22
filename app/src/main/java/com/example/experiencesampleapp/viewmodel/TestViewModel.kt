package com.example.experiencesampleapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.experiencesampleapp.R
import com.example.experiencesampleapp.entity.CurrentWorker
import com.example.experiencesampleapp.entity.Record
import com.example.experiencesampleapp.function.InsertTestData
import com.example.experiencesampleapp.function.timeTransform
import com.example.experiencesampleapp.function.timestampToDate
import com.example.experiencesampleapp.repository.CurrentWorkerRepository
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import com.example.experiencesampleapp.repository.QuestionRepository
import com.example.experiencesampleapp.repository.RecordRepository
import com.example.experiencesampleapp.repository.UserSettingRepository
import com.example.experiencesampleapp.workers.SendMessageWorker
import com.opencsv.CSVWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random


interface TestViewModelAbstract {

}

@Suppress("CAST_NEVER_SUCCEEDS")
@HiltViewModel
class TestViewModel @Inject constructor(
    private val phishingMessageRepository: PhishingMessageRepository,
    private val currentWorkerRepository: CurrentWorkerRepository,
    private val recordRepository: RecordRepository,
    private val questionRepository: QuestionRepository,
    private val userSettingRepository: UserSettingRepository,
    private val application: Application,
) : ViewModel(), TestViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val workManager = WorkManager.getInstance(application.applicationContext)

    private val startHour = userSettingRepository.getStartHour()
    private val startMinute = userSettingRepository.getStartMinute()
    private val endHour = userSettingRepository.getEndHour()
    private val endMinute = userSettingRepository.getEndMinute()
    private val frequency = userSettingRepository.getFrequency()
    private val duration = userSettingRepository.getDuration()
    private val pattern = userSettingRepository.getPattern()
    private val isTestStart = userSettingRepository.getIsTestStart()
    private val isTestFinish = userSettingRepository.getIsTestFinish()
    private val isDataExport = userSettingRepository.getIsDataExport()
    private val fileName = userSettingRepository.getFileName()

    var isDialogOpen = false

    fun getAllRecordsAsFlow(): Flow<List<Record>> {
        return recordRepository.getAllRecordsAsFlow()
    }

    fun updateRecord(record: Record) {
        ioScope.launch {
            recordRepository.updateRecord(record)
        }
    }

    fun insertPhishingMessage() {
        InsertTestData(phishingMessageRepository, questionRepository).insertPhishingMessage()
    }

    fun insertQuestion() {
        InsertTestData(phishingMessageRepository, questionRepository).insertQuestion()
    }

    private fun insertCurrentWorker(currentWorker: CurrentWorker) {
        ioScope.launch {
            currentWorkerRepository.insertCurrentWorker(currentWorker)
        }
    }

    fun deleteAllData() {
        ioScope.launch {
            currentWorkerRepository.deleteAllData()
            phishingMessageRepository.deleteAllData()
            questionRepository.deleteAllData()
            recordRepository.deleteAllData()

        }
    }

    fun getStartHour(): Flow<Int> {
        return startHour
    }

    fun getStartMinute(): Flow<Int> {
        return startMinute
    }

    fun getEndHour(): Flow<Int> {
        return endHour
    }

    fun getEndMinute(): Flow<Int> {
        return endMinute
    }

    fun getFrequency(): Flow<Int> {
        return frequency
    }

    fun getDuration(): Flow<Int> {
        return duration
    }

    fun getPattern(): Flow<Int> {
        return pattern
    }

    fun getIsTestStart(): Flow<Boolean> {
        return isTestStart
    }

    fun getIsTestFinish(): Flow<Boolean> {
        return isTestFinish
    }

    fun getIsDataExport(): Flow<Boolean> {
        return isDataExport
    }

    fun getFileName(): Flow<String> {
        return fileName
    }

    fun setStartHour(hour: Int) {
        ioScope.launch {
            userSettingRepository.setStartHour(hour)
        }
    }

    fun setStartMinute(minute: Int) {
        ioScope.launch {
            userSettingRepository.setStartMinute(minute)
        }
    }

    fun setEndHour(hour: Int) {
        ioScope.launch {
            userSettingRepository.setEndHour(hour)
        }
    }

    fun setEndMinute(minute: Int) {
        ioScope.launch {
            userSettingRepository.setEndMinute(minute)
        }
    }

    fun setFrequency(frequency: Int) {
        ioScope.launch {
            userSettingRepository.setFrequency(frequency)
        }
    }

    fun setDuration(duration: Int) {
        ioScope.launch {
            userSettingRepository.setDuration(duration)
        }
    }

    fun setPattern(pattern: Int) {
        ioScope.launch {
            userSettingRepository.setPattern(pattern)
        }
    }

    fun setIsTestStart(isTestStart: Boolean) {
        ioScope.launch {
            userSettingRepository.setIsTestStart(isTestStart)
        }
    }

    fun setIsTestFinish(isTestFinish: Boolean) {
        ioScope.launch {
            userSettingRepository.setIsTestFinish(isTestFinish)
        }
    }

    fun setIsDataExport(isDataExport: Boolean) {
        ioScope.launch {
            userSettingRepository.setIsDataExport(isDataExport)
        }
    }

    fun setFileName(fileName: String) {
        ioScope.launch {
            userSettingRepository.setFileName(fileName)
        }
    }

    @SuppressLint("MissingPermission")
    fun makeNotification(title: String, text: String) {
        //create a notification
        val notificationBuilder = NotificationCompat.Builder(application.applicationContext, "1")
            .setSmallIcon(R.drawable.messages_icon).setContentTitle(title)
            .setContentText(text).setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        //show the notification
        NotificationManagerCompat.from(application.applicationContext)
            .notify(Random.nextInt(), notificationBuilder.build())
    }

    //for test
    fun messageSendWorker(hour: Int, minute: Int) {

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val workerNumber = Data.Builder().putString("worker_number", "last").build()
        val oneTimeWorkRequest =
            OneTimeWorkRequestBuilder<SendMessageWorker>().setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            ).setInitialDelay(timeTransform(hour, minute), TimeUnit.MILLISECONDS)
                .setInputData(workerNumber)
                .addTag("last")
                .build()
        workManager.enqueueUniqueWork(
            "everyDayMessageSendTriggerlast",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun messageSendFixedSamplingScheme(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        frequency: Int,
        durationDays: Int,
    ) {

        val calendar = Calendar.getInstance()
        val nowMillis: Long = calendar.timeInMillis
        var count = 0

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        var startTimePoint: Long = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, endHour)
        calendar.set(Calendar.MINUTE, endMinute)
        var endTimePoint: Long = calendar.timeInMillis
        val gap = (endTimePoint - startTimePoint) / (frequency - 1)

        for (i in 1..durationDays) {
            startTimePoint += 86400000
            endTimePoint += 86400000
            for (j in startTimePoint..endTimePoint step gap) {
                count++
                val workerNumber =
                    if (count == (durationDays * frequency)) Data.Builder().putString(
                        "worker_number",
                        "last"
                    ).build()
                    else Data.Builder().putString("worker_number", count.toString()).build()

                val oneTimeWorkRequest =
                    OneTimeWorkRequestBuilder<SendMessageWorker>().setConstraints(
                        Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                    ).setInitialDelay(j - nowMillis, TimeUnit.MILLISECONDS)
                        .setInputData(workerNumber)
                        .addTag(count.toString())
                        .build()
                workManager.enqueueUniqueWork(
                    "everyDayMessageSendTrigger$count",
                    ExistingWorkPolicy.REPLACE,
                    oneTimeWorkRequest
                )
                //workManager.enqueue(oneTimeWorkRequest)

                insertCurrentWorker(
                    CurrentWorker(
                        id = count,
                        workerName = "everyDayMessageSendTrigger$count",
                        workerState = 0,
                        time = timestampToDate(j)
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun messageSendRandomSamplingScheme(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        frequency: Int,
        durationDays: Int,
    ) {

        val calendar = Calendar.getInstance()
        val nowMillis: Long = calendar.timeInMillis
        var count = 0

        var randomTimePoint: Long

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        var startTimePoint: Long = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, endHour)
        calendar.set(Calendar.MINUTE, endMinute)
        var endTimePoint = calendar.timeInMillis

        for (i in 1..durationDays) {
            startTimePoint += 86400000
            endTimePoint += 86400000
            for (j in 1..frequency) {
                count++
                randomTimePoint = Random.nextLong(startTimePoint, endTimePoint)
                val workerNumber =
                    if (count == (durationDays * frequency)) Data.Builder().putString(
                        "worker_number",
                        "last"
                    ).build()
                    else Data.Builder().putString("worker_number", count.toString()).build()

                val oneTimeWorkRequest =
                    //last notification
                    OneTimeWorkRequestBuilder<SendMessageWorker>().setConstraints(
                        Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                    ).setInitialDelay(randomTimePoint - nowMillis, TimeUnit.MILLISECONDS)
                        .setInputData(workerNumber)
                        .addTag("last")
                        .build()

                workManager.enqueueUniqueWork(
                    "everyDayMessageSendTrigger$count",
                    ExistingWorkPolicy.REPLACE,
                    oneTimeWorkRequest
                )
                //workManager.enqueue(oneTimeWorkRequest)
                insertCurrentWorker(
                    CurrentWorker(
                        id = count,
                        workerName = "everyDayMessageSendTrigger$count",
                        workerState = 0,
                        time = timestampToDate(randomTimePoint)
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun messageSendSemiRandomSamplingScheme(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        frequency: Int,
        durationDays: Int,
    ) {

        val calendar = Calendar.getInstance()
        val nowMillis: Long = calendar.timeInMillis
        var startTimePoint: Long = 0
        var endTimePoint: Long = 0
        var count = 0

        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        startTimePoint = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, endHour)
        calendar.set(Calendar.MINUTE, endMinute)
        endTimePoint = calendar.timeInMillis
        val gap = (endTimePoint - startTimePoint) / (frequency - 1)

        for (i in 1..durationDays) {
            startTimePoint += 86400000
            endTimePoint += 86400000
            for (j in startTimePoint..endTimePoint step gap) {
                val tempTimePoint = j + Random.nextLong(0, gap)
                count++
                val workerNumber =
                    if (count == (durationDays * frequency)) Data.Builder().putString(
                        "worker_number",
                        "last"
                    ).build()
                    else Data.Builder().putString("worker_number", count.toString()).build()
                val oneTimeWorkRequest =
                    OneTimeWorkRequestBuilder<SendMessageWorker>()
                        .setConstraints(
                            Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                                .build()
                        )
                        .setInitialDelay(tempTimePoint - nowMillis, TimeUnit.MILLISECONDS)
                        .setInputData(workerNumber)
                        .addTag(count.toString())
                        .build()
                workManager.enqueueUniqueWork(
                    "everyDayMessageSendTrigger$count",
                    ExistingWorkPolicy.REPLACE,
                    oneTimeWorkRequest
                )
                //workManager.enqueue(oneTimeWorkRequest)
                insertCurrentWorker(
                    CurrentWorker(
                        id = count,
                        workerName = "everyDayMessageSendTrigger$count",
                        workerState = 0,
                        time = timestampToDate(tempTimePoint)
                    )
                )
            }
        }
    }

    fun lastWorkerIsDone(duration: Int, frequency: Int): Boolean {
        val lastNotificationNumber = (duration * frequency).toString()
        return if (workManager.getWorkInfosByTag("54").get().isNullOrEmpty()) {
            false
        } else workManager.getWorkInfosByTag("54").get().last().state.isFinished
    }

    fun cancelAllWorker() {
        workManager.cancelAllWork()
    }

    @SuppressLint("ObsoleteSdkInt")
    fun exportAllRecordsToCSV() {
        val fileName = Calendar.getInstance().timeInMillis.toString() + "_record.csv"
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val csvFile: File = File(path, fileName)

        ioScope.launch {
            try {
                val fileWriter = FileWriter(csvFile)
                val csvWriter = CSVWriter(fileWriter)
                val recordsList = recordRepository.getAllRecords()

                // Writing header
                csvWriter.writeNext(
                    arrayOf(
                        "No",
                        "message",
                        "send_time",
                        "respond_type",
                        "respond_time",
                        "question",
                        "answers",
                        "answer_time"
                    )
                )

                // Writing data rows
                for (record in recordsList) {
                    csvWriter.writeNext(
                        arrayOf(
                            java.lang.String.valueOf(record.id),
                            java.lang.String.valueOf(record.message),
                            java.lang.String.valueOf(record.send_time),
                            java.lang.String.valueOf(record.respond_type),
                            java.lang.String.valueOf(record.respond_time),
                            java.lang.String.valueOf(record.question),
                            java.lang.String.valueOf(record.answers),
                            java.lang.String.valueOf(record.answer_time)
                        )
                    )
                }
                csvWriter.close()
                fileWriter.close()
                userSettingRepository.setFileName(fileName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}