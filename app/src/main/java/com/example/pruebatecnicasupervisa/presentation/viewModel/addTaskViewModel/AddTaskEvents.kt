package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import com.example.pruebatecnicasupervisa.data.model.Priority
import com.example.pruebatecnicasupervisa.data.model.State

sealed interface AddTaskEvents {
    data class SetTitle(val title: String): AddTaskEvents
    data class SetDescription(val description: String): AddTaskEvents
    data class SetPriority(val priority: Priority): AddTaskEvents
    data class SetState(val state: State): AddTaskEvents
}