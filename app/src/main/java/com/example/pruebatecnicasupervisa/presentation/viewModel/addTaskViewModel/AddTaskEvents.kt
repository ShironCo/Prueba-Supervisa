package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

sealed interface AddTaskEvents {
    data class SetTitle(val title: String): AddTaskEvents
    data class SetDescription(val description: String): AddTaskEvents
}