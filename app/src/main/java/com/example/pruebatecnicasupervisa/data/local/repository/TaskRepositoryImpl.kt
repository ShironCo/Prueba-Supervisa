package com.example.pruebatecnicasupervisa.data.local.repository

import com.example.pruebatecnicasupervisa.data.local.dao.TaskDao
import com.example.pruebatecnicasupervisa.data.mapper.toDomain
import com.example.pruebatecnicasupervisa.data.mapper.toEntity
import com.example.pruebatecnicasupervisa.domain.model.Task
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {
    override suspend fun getTasks(): Flow<List<Task>> {
        val entities = taskDao.getTasks()
        return entities.map { list -> list.map{it.toDomain()}
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }
}