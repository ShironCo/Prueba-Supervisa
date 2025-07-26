package com.example.pruebatecnicasupervisa.domain.repository

import com.example.pruebatecnicasupervisa.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks(): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
}