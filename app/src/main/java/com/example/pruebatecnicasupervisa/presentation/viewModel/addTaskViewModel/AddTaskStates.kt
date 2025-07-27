package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task

data class AddTaskStates(
    val title:String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val priority: Priority? = null,
    val status: State? = null,
    val taskList: List<Task> = emptyList(),
    val errorMessage: String = "",
    val snackBarMessage: String = ""
)
