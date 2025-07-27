package com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val states = MutableStateFlow(TaskStates())

    init {
        viewModelScope.launch {
            taskRepository.getTasks().onSuccess { flow ->
                flow.collectLatest { tasks ->
                    states.update {
                        it.copy(
                            taskList = tasks
                        )
                    }
                }

            }
        }
    }

    fun onEvent(events: TaskEvents) {
        when (events) {
            is TaskEvents.FilterTask -> {
                viewModelScope.launch {
                    taskRepository.filterTasks(
                        states.value.filterPriority,
                        states.value.filterStates
                    ).onSuccess { flow ->
                        flow.collectLatest { tasks ->
                            states.update {
                                it.copy(
                                    taskList = tasks
                                )
                            }
                        }

                    }
                }
            }

            is TaskEvents.SetFilterPriority -> {
                states.update {
                    it.copy(
                        filterPriority = events.priority
                    )
                }
            }

            is TaskEvents.SetFilterState -> {
                states.update {
                    it.copy(
                        filterStates = events.state
                    )
                }
            }
        }
    }


}