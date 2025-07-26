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

    init {
        viewModelScope.launch {
            taskRepository.getTasks().collectLatest { list ->
                states.update {
                    it.copy(
                        taskList = list
                    )
                }
            }
        }
    }

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
                it.copy(state = events.state)
            }

            AddTaskEvents.AddTask -> {
                if (states.value.title.isBlank()) {
                    states.update {
                        it.copy(errorMessage = "El título es obligatorio")
                    }
                    return
                }
                if (states.value.priority == null) {
                    states.update {
                        it.copy(errorMessage = "La prioridad es obligatoria")
                    }
                    return
                }
                if (states.value.state == null) {
                    states.update {
                        it.copy(errorMessage = "El estado es obligatorio")
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
                            status = it.state!!
                        ),
                        title = "",
                        description = "",
                        dueDate = null,
                        priority = null,
                        state = null,
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
                    taskRepository.insertTask(events.task)
                }
            }
        }
    }
}