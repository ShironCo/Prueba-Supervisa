package com.example.pruebatecnicasupervisa.domain.repository

import com.example.pruebatecnicasupervisa.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks(): Result<Flow<List<Task>>>
    suspend fun insertTask(task: Task) : Result<Unit>
    suspend fun deleteTask(task: Task)
}