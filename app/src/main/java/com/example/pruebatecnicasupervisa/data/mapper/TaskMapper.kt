package com.example.pruebatecnicasupervisa.data.mapper

import com.example.pruebatecnicasupervisa.data.local.model.TaskEntity
import com.example.pruebatecnicasupervisa.domain.model.Task

// De Entity (Room) a Domain
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

// De Domain a Entity
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
