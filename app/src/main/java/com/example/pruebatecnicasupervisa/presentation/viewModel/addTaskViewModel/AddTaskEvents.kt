package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task

sealed interface AddTaskEvents {
    data class SetTitle(val title: String): AddTaskEvents
    data class SetDescription(val description: String): AddTaskEvents
    data class SetDueDate(val dueDate: Long?): AddTaskEvents
    data class SetPriority(val priority: Priority): AddTaskEvents
    data class SetState(val state: State): AddTaskEvents

    data class DeleteTask(val task: Task): AddTaskEvents
    data object AddTask : AddTaskEvents
    data class SaveTask(val task: Task) : AddTaskEvents
    data object ClearSnackBarMessage: AddTaskEvents
}