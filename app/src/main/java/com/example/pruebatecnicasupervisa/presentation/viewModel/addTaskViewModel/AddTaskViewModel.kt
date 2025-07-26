package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import androidx.lifecycle.ViewModel
import com.example.pruebatecnicasupervisa.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor() : ViewModel() {

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
                it.copy(state = events.state)
            }

            AddTaskEvents.SaveTask -> {
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
                            title = it.title,
                            description = it.description,
                            dueDate = it.dueDate,
                            priority = it.priority!!,
                            state = it.state!!
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
        }
    }
}