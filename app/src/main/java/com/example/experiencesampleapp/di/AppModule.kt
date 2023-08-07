package com.example.experiencesampleapp.di

import android.app.Application
import android.content.Context
import com.example.experiencesampleapp.data_source.CurrentWorkerDao
import com.example.experiencesampleapp.data_source.DateStoreManager
import com.example.experiencesampleapp.data_source.ExperienceSampleAppDatabase
import com.example.experiencesampleapp.data_source.PhishingMessageDao
import com.example.experiencesampleapp.data_source.QuestionDao
import com.example.experiencesampleapp.data_source.RecordDao
import com.example.experiencesampleapp.repository.CurrentWorkerRepository
import com.example.experiencesampleapp.repository.PhishingMessageRepository
import com.example.experiencesampleapp.repository.QuestionRepository
import com.example.experiencesampleapp.repository.RecordRepository
import com.example.experiencesampleapp.repository.UserSettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideExperienceSampleAppDatabase(app: Application): ExperienceSampleAppDatabase {
        return ExperienceSampleAppDatabase.getInstance(context = app)
    }

    @Singleton
    @Provides
    fun providePhishingMessageRepository(phishingMessageDao: PhishingMessageDao): PhishingMessageRepository {
        return PhishingMessageRepository(phishingMessageDao = phishingMessageDao)
    }

    @Singleton
    @Provides
    fun providePhishingMessageDao(experienceSampleAppDatabase: ExperienceSampleAppDatabase): PhishingMessageDao {
        return experienceSampleAppDatabase.phishingMessageDao
    }

    @Singleton
    @Provides
    fun provideCurrentWorkerRepository(currentWorkerDao: CurrentWorkerDao): CurrentWorkerRepository {
        return CurrentWorkerRepository(currentWorkerDao = currentWorkerDao)
    }

    @Singleton
    @Provides
    fun provideCurrentWorkerDao(experienceSampleAppDatabase: ExperienceSampleAppDatabase): CurrentWorkerDao {
        return experienceSampleAppDatabase.currentWorkerDao
    }

    @Singleton
    @Provides
    fun provideRecordRepository(recordDao: RecordDao): RecordRepository {
        return RecordRepository(recordDao = recordDao)
    }

    @Singleton
    @Provides
    fun provideRecordDao(experienceSampleAppDatabase: ExperienceSampleAppDatabase): RecordDao {
        return experienceSampleAppDatabase.recordDao
    }

    @Singleton
    @Provides
    fun provideQuestionRepository(questionDao: QuestionDao): QuestionRepository {
        return QuestionRepository(questionDao = questionDao)
    }

    @Singleton
    @Provides
    fun provideQuestionDao(experienceSampleAppDatabase: ExperienceSampleAppDatabase): QuestionDao {
        return experienceSampleAppDatabase.questionDao
    }

    @Singleton
    @Provides
    fun provideUserSettingRepository(dateStoreManager: DateStoreManager): UserSettingRepository {
        return UserSettingRepository(dateStoreManager)
    }

    @Singleton
    @Provides
    fun provideDateStoreManager(@ApplicationContext context: Context): DateStoreManager {
        return DateStoreManager(context)
    }
}