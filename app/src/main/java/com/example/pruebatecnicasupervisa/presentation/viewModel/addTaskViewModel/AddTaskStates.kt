package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import com.example.pruebatecnicasupervisa.data.model.Priority
import com.example.pruebatecnicasupervisa.data.model.State
import java.time.LocalDate

data class AddTaskStates(
    val title:String = "",
    val description: String = "",
    val dueDate: LocalDate? = null,
    val priority : Priority? = null,
    val state: State? = null
)
