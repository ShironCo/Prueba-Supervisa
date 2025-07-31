package com.example.pruebatecnicasupervisa.data.mapper

import com.example.pruebatecnicasupervisa.data.local.model.TaskEntity
import com.example.pruebatecnicasupervisa.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(
        task_id = task_id,
        title = title,
        description = description,
        due_Date = due_Date,
        priority = priority,
        status = status
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        task_id = task_id,
        title = title,
        description = description,
        due_Date = due_Date,
        priority = priority,
        status = status
    )
}
