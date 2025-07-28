package com.example.pruebatecnicasupervisa.domain.repository

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks(): Result<Flow<List<Task>>>
    suspend fun insertTask(task: Task) : Result<Unit>
    suspend fun deleteTask(taskList: Set<Task>) : Result<Unit>
    suspend fun deleteTaskById(taskId: String) : Result<Unit>
    suspend fun filterTasks(priority: Priority?, state: State?): Result<Flow<List<Task>>>
}