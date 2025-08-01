package com.example.pruebatecnicasupervisa.data.local.repository

import com.example.pruebatecnicasupervisa.data.local.dao.TaskDao
import com.example.pruebatecnicasupervisa.data.mapper.toDomain
import com.example.pruebatecnicasupervisa.data.mapper.toEntity
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {
    override suspend fun getTasks(): Result<Flow<List<Task>>> {
        val entities = taskDao.getTasks()
        return try{
            Result.success(entities.map { list -> list.map{it.toDomain()}})
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun insertTask(task: Task) : Result<Unit>{
        return try {
            Result.success(taskDao.insertTask(task.toEntity()))
        }catch (e: Exception){
            Result.failure(e)
        }

    }

    override suspend fun deleteTask(taskList: Set<Task>): Result<Unit> {
        return try {
            taskList.forEach {
                taskDao.deleteTask(it.toEntity())
            }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteTaskById(taskId: String): Result<Unit> {
        return try {
            taskDao.deleteTaskById(taskId)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun filterTasks(priority: Priority?, state: State?): Result<Flow<List<Task>>> {
        return try{
            val entities = taskDao.filterTasks(
                priority = priority,
                status = state
            )
            Result.success(entities.map { list -> list.map{it.toDomain()}})
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}