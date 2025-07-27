package com.example.pruebatecnicasupervisa.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnicasupervisa.data.local.model.TaskEntity
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)


    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query(
        """ SELECT * FROM TaskEntity 
    WHERE (:status IS NULL OR status = :status)
      AND (:priority IS NULL OR priority = :priority) """
    )
    fun filterTasks(
        status: State?,
        priority: Priority?
    ): Flow<List<TaskEntity>>
}