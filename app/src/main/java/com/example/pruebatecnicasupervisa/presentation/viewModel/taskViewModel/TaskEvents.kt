package com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State

sealed interface TaskEvents {
    data object FilterTask: TaskEvents
    data class SetFilterPriority(val priority: Priority?): TaskEvents
    data class SetFilterState( val state: State?): TaskEvents
}