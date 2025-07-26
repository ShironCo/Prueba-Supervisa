package com.example.pruebatecnicasupervisa.data.model

import java.time.LocalDate

data class Task(
    val title: String,
    val description: String? = null,
    val dueDate: LocalDate? = null,
    val priority : Priority,
    val state: State
)
