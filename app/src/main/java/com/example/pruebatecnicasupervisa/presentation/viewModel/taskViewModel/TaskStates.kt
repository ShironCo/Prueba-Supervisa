package com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Task

data class TaskStates(
    val taskList: List<Task> = emptyList()
)
