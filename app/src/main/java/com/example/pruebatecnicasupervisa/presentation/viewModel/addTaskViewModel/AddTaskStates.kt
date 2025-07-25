package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import java.time.LocalDate

data class AddTaskStates(
    val title:String = "",
    val description: String = "",
    val dueDate: LocalDate? = null,
    val priority : String = "",
    val state: String = ""
)
