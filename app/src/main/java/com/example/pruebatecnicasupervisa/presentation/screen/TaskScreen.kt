package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.presentation.ui.components.TaskCard
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar
import com.example.pruebatecnicasupervisa.presentation.ui.navigation.NavigationRoutes
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskStates
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskViewModel

@Composable
fun TaskScreen(
    navigationHostController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel()
) {

    val states by viewModel.states.collectAsState()
    LaunchedEffect(
        states.filterStates, states.filterPriority
    ) {
        viewModel.onEvent(TaskEvents.FilterTask)
    }
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
        ) { event ->
            viewModel.onEvent(event)
        }
    }
}

@Composable
fun TaskBody(
    modifier: Modifier,
    states: TaskStates,
    onClick: (TaskEvents) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            item {
                LazyRow {
                    item {
                        DropBoxFilter(
                            clearName = "Todas las prioridades",
                            listItems = listOf(
                                Priority.HIGH.label,
                                Priority.MEDIUM.label,
                                Priority.LOW.label,
                            )
                        ) { selectedLabel ->
                            val selectedPriority =
                                if (selectedLabel == "Todas las prioridades") null
                                else Priority.entries.find { it.label == selectedLabel }
                            onClick(TaskEvents.SetFilterPriority(selectedPriority))
                        }

                    }
                    item {
                        DropBoxFilter(
                            clearName = "Todos los estados",
                            listItems = listOf(
                                State.PENDING.label,
                                State.IN_PROGRESS.label,
                                State.COMPLETED.label,
                            )
                        ) { selectedLabel ->
                            val selectedState = if (selectedLabel == "Todos los estados") null
                            else State.entries.find { it.label == selectedLabel }
                            onClick(TaskEvents.SetFilterState(selectedState))
                        }
                    }
                    item { }
                }
            }
            items(states.taskList.size) {
                TaskCard(
                    title = states.taskList[it].title,
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

@Composable
fun DropBoxFilter(
    clearName: String,
    listItems: List<String>,
    onClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Button(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.outline
            )
        ) {
            Text(text = buttonText.ifBlank { clearName })
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Filtrar por prioridad")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.outline
        ) {
            listItems.forEach {
                DropdownMenuItem(
                    text = { Text(it, color = MaterialTheme.colorScheme.onSecondary) },
                    onClick = {
                        buttonText = it
                        expanded = false
                        onClick(buttonText)
                    }
                )

            }
            DropdownMenuItem(
                text = { Text(clearName, color = MaterialTheme.colorScheme.onSecondary) },
                onClick = {
                    buttonText = clearName
                    expanded = false
                    onClick(buttonText)
                }
            )
        }
    }
}