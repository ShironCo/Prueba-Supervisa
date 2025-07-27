package com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel

import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task

sealed interface TaskEvents {
    data object FilterTask : TaskEvents
    data class SetFilterPriority(val priority: Priority?) : TaskEvents
    data class SetFilterState(val state: State?) : TaskEvents
    data class AddTaskList(val task: Task) : TaskEvents
    data class RemoveTaskList(val task: Task) : TaskEvents
    data object CleanTaskList : TaskEvents
    data object DeleteTasks: TaskEvents
}