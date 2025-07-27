package com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicasupervisa.domain.model.Task
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    val states = MutableStateFlow(EditTaskStates())

    fun onEvent(events: EditTaskEvents){
        when(events){
            is EditTaskEvents.SetDescription -> {
                states.update {
                    it.copy(description = events.description)
                }
            }

            is EditTaskEvents.SetTitle ->
                states.update {
                    it.copy(title = events.title)
                }

            is EditTaskEvents.SetPriority -> states.update {
                it.copy(priority = events.priority)
            }

            is EditTaskEvents.SetState -> states.update {
                it.copy(status = events.state)
            }

            is EditTaskEvents.SetDueDate -> {
                states.update {
                    it.copy(
                        dueDate = events.dueDate
                    )
                }
            }

            EditTaskEvents.SaveTask -> {
                if (states.value.idTask.isNullOrBlank()) {
                    return
                }
                if (states.value.title.isNullOrBlank()) {
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
                viewModelScope.launch {
                    val task = Task(
                        task_id = states.value.idTask!!,
                        title = states.value.title!!,
                        description = states.value.description,
                        due_Date = states.value.dueDate,
                        priority = states.value.priority!!,
                        status = states.value.status!!
                    )
                    Log.d("OBJETO", task.toString())
                    taskRepository.insertTask(task).onSuccess {
                    }
                }
            }

            is EditTaskEvents.SetId -> {
                states.update {
                    it.copy(
                        idTask = events.id
                    )
                }
            }
        }
        }
    }
