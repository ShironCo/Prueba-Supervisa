package com.example.pruebatecnicasupervisa.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebatecnicasupervisa.data.local.dao.TaskDao
import com.example.pruebatecnicasupervisa.data.local.model.TaskEntity


@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: TaskDao
}