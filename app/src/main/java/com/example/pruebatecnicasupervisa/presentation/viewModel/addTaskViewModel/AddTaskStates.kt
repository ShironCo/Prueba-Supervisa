package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import com.example.pruebatecnicasupervisa.data.model.Priority
import com.example.pruebatecnicasupervisa.data.model.State
import com.example.pruebatecnicasupervisa.data.model.Task

data class AddTaskStates(
    val title:String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val priority: Priority? = null,
    val state: State? = null,
    val taskList: List<Task> = emptyList(),
    val errorMessage: String = ""
)
