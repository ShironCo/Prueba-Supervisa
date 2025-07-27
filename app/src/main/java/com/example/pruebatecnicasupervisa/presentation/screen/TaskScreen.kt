package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pruebatecnicasupervisa.presentation.ui.components.TaskCard
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar
import com.example.pruebatecnicasupervisa.presentation.ui.navigation.NavigationRoutes
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskStates
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskViewModel

@Composable
fun TaskScreen(
    navigationHostController: NavHostController,
    viewModel : TaskViewModel = hiltViewModel()
) {

    val states by viewModel.states.collectAsState()

    Scaffold(
        topBar = {
            TopBar(title = "Tareas", showIcon = false) {

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigationHostController.navigate(NavigationRoutes.AddTaskScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Tarea")
            }
        }
    ) {
        TaskBody(
            modifier = Modifier.padding(it),
            states = states
        )
    }
}

@Composable
fun TaskBody(
    modifier: Modifier,
    states: TaskStates
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(states.taskList.size){
                TaskCard(title = states.taskList[it].title,
                    description = states.taskList[it].description,
                    dueDate = states.taskList[it].due_Date,
                    priority = states.taskList[it].priority,
                    state = states.taskList[it].status,
                    maxLines = 2
                ) {}
            }
        }
    }

}