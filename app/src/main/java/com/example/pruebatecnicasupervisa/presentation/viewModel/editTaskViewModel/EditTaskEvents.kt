package com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State

sealed interface EditTaskEvents {
    data class SetId(val id: String): EditTaskEvents
    data class SetTitle(val title: String): EditTaskEvents
    data class SetDescription(val description: String?): EditTaskEvents
    data class SetDueDate(val dueDate: Long?): EditTaskEvents
    data class SetPriority(val priority: Priority): EditTaskEvents
    data class SetState(val state: State): EditTaskEvents
    data object SaveTask: EditTaskEvents
}