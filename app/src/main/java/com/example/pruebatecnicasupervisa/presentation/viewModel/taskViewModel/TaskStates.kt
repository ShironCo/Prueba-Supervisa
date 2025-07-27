package com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task

data class TaskStates(
    val taskList: List<Task> = emptyList(),
    val filterPriority: Priority? = null,
    val filterStates: State? = null,
    val taskListSelected: Set<Task> = emptySet(),
    val progressIndicatorMessage : String? = null,
    val currentTaskEdit: Task? = null,
)
