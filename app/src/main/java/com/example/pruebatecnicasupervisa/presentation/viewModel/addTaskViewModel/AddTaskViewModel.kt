package com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(): ViewModel(){

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
        }
    }
    
}