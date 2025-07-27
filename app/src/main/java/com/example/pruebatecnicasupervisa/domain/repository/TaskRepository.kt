package com.example.pruebatecnicasupervisa.domain.repository

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks(): Result<Flow<List<Task>>>
    suspend fun insertTask(task: Task) : Result<Unit>
    suspend fun deleteTask(task: Task)
    suspend fun filterTasks(priority: Priority?, state: State?): Result<Flow<List<Task>>>
}