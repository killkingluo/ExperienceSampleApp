package com.example.experiencesampleapp.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.experiencesampleapp.entity.CurrentWorker
import com.example.experiencesampleapp.entity.PhishingMessage
import com.example.experiencesampleapp.entity.Question
import com.example.experiencesampleapp.entity.Record

@Database(entities = [PhishingMessage::class, CurrentWorker::class, Record::class, Question::class], version = 2                                                                                        , exportSchema = false)
abstract class ExperienceSampleAppDatabase: RoomDatabase() {
    abstract val phishingMessageDao: PhishingMessageDao
    abstract val currentWorkerDao: CurrentWorkerDao
    abstract val recordDao: RecordDao
    abstract val questionDao: QuestionDao

    //initial db
    companion object {
        private var INSTANCE: ExperienceSampleAppDatabase? = null
        fun getInstance(context: Context): ExperienceSampleAppDatabase{
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, ExperienceSampleAppDatabase::class.java, "experienceSample_db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as ExperienceSampleAppDatabase
        }
    }
}