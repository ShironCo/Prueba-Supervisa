package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicasupervisa.domain.model.Task
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val states = MutableStateFlow(AddTaskStates())

    fun onEvent(events: AddTaskEvents) {
        when (events) {
            is AddTaskEvents.SetDescription -> {
                states.update {
                    it.copy(description = events.description)
                }
            }

            is AddTaskEvents.SetTitle ->
                states.update {
                    it.copy(title = events.title)
                }

            is AddTaskEvents.SetPriority -> states.update {
                it.copy(priority = events.priority)
            }

            is AddTaskEvents.SetState -> states.update {
                it.copy(status = events.state)
            }

            AddTaskEvents.AddTask -> {
                if (states.value.title.isBlank()) {
                    states.update {
                        it.copy(errorMessage = "title")
                    }
                    return
                }
                if (states.value.priority == null) {
                    states.update {
                        it.copy(errorMessage = "priority")
                    }
                    return
                }
                if (states.value.status == null) {
                    states.update {
                        it.copy(errorMessage = "status")
                    }
                    return
                }
                states.update {
                    it.copy(
                        taskList = it.taskList + Task(
                            task_id = UUID.randomUUID().toString(),
                            title = it.title,
                            description = it.description,
                            due_Date = it.dueDate,
                            priority = it.priority!!,
                            status = it.status!!
                        ),
                        title = "",
                        description = "",
                        dueDate = null,
                        priority = null,
                        status = null,
                        errorMessage = ""
                    )
                }
            }

            is AddTaskEvents.SetDueDate -> {
                states.update {
                    it.copy(
                        dueDate = events.dueDate
                    )
                }
            }

            is AddTaskEvents.DeleteTask -> {
                states.update {
                    it.copy(
                        taskList = it.taskList - events.task
                    )
                }
            }

            is AddTaskEvents.SaveTask -> {
                viewModelScope.launch {
                    taskRepository.insertTask(events.task).onSuccess {
                        states.update {
                            it.copy(
                                taskList = it.taskList - events.task
                            )
                        }
                    }
                }
            }
        }
    }
}