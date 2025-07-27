package com.example.pruebatecnicasupervisa.di

import android.app.Application
import android.os.Vibrator
import androidx.room.Database
import androidx.room.Room
import com.example.pruebatecnicasupervisa.data.local.dao.TaskDao
import com.example.pruebatecnicasupervisa.data.local.database.AppDatabase
import com.example.pruebatecnicasupervisa.data.local.repository.TaskRepositoryImpl
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideTaskDao(application: Application): TaskDao{
        val db = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "task_db"
            ).build()
        return db.dao
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository{
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideVibrator(context: Application): Vibrator{
        return context.getSystemService(Vibrator::class.java) as Vibrator
    }
}