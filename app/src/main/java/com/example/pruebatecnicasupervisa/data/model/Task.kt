package com.example.pruebatecnicasupervisa.data.model

import java.time.LocalDate

data class Task(
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val priority : Priority,
    val state: State
)
