package com.example.pruebatecnicasupervisa.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val task_id: String,
    val title: String,
    val description: String? = null,
    val due_Date: Long? = null,
    val priority : Priority,
    val status: State
)
