package com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task

data class EditTaskStates(
    val idTask: String? = null,
    val title:String? = null,
    val description: String? = null,
    val dueDate: Long? = null,
    val priority: Priority? = null,
    val status: State? = null,
    val taskList: List<Task> = emptyList(),
    val errorMessage: String = "",
)
