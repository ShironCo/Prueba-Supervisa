package com.example.pruebatecnicasupervisa.domain.model

import androidx.room.Entity


@Entity
data class Task(
    val task_id: String,
    val title: String,
    val description: String? = null,
    val due_Date: Long? = null,
    val priority : Priority,
    val status: State
)
