package com.example.pruebatecnicasupervisa.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.presentation.ui.components.TaskCard
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar
import com.example.pruebatecnicasupervisa.presentation.ui.navigation.NavigationRoutes
import com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel.AddTaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel.EditTaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskStates
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
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
    var alertDialog by remember {
        mutableStateOf(false)
    }

    BackHandler(
        states.currentTaskEdit != null
    ) {
        viewModel.onEvent(TaskEvents.SetCurrentTaskEdit(null))
    }

    if (alertDialog) {
        AlertDialog(
            onDismissRequest = { alertDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onEvent(TaskEvents.DeleteTasks)
                    alertDialog = false
                }) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    alertDialog = false
                }) {
                    Text(text = "Cancelar")
                }
            },
            title = {
                Text(
                    text = "¿Seguro deseas eliminar la tarea d?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            text = {
                Text(
                    text = "Se eliminara la tarea de forma permanente, no habra forma de recuperarla",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                )
            }
        )
    }


    AnimatedVisibility(visible = states.progressIndicatorMessage != null) {
        states.progressIndicatorMessage?.let {
            Dialog(onDismissRequest = {}) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            if (states.taskListSelected.isNotEmpty()) {
                TopAppBar(title = {
                    Text(
                        text = states.taskListSelected.size.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }, navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(TaskEvents.CleanTaskList)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver a la pantalla principal"
                        )
                    }
                },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.surface,
                        navigationIconContentColor = MaterialTheme.colorScheme.surface
                    ),
                    actions = {
                        IconButton(onClick = {
                            alertDialog = true
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Borrar tareas",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                )
            } else {
                if (states.currentTaskEdit != null) {
                    TopBar(title = "Editar tarea", showIcon = true) {
                        viewModel.onEvent(TaskEvents.SetCurrentTaskEdit(null))
                    }
                } else {
                    TopBar(title = "Tareas")
                }

            }
        },
        floatingActionButton = {
            if (states.currentTaskEdit == null)
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
    ) { padding ->
        TaskBody(
            modifier = Modifier.padding(padding),
            navigationHostController = navigationHostController,
            states = states
        ) { event ->
            viewModel.onEvent(event)
        }
        AnimatedVisibility(
            visible = states.currentTaskEdit != null,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth},
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth},
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        ) {
            states.currentTaskEdit?.let {
                EditTaskScreen(
                    modifier = Modifier.padding(padding),
                    it,
                    viewModelTask = viewModel
                )
            }

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskBody(
    modifier: Modifier,
    states: TaskStates,
    navigationHostController : NavHostController,
    onClick: (TaskEvents) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            item {
                Button(
                    onClick = {
                        navigationHostController.navigate(NavigationRoutes.PokemonScreen.route)
                    }
                ) {
                    Text("Ir a PokemonApi")
                }
            }
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
                    item {}
                }
            }
            items(states.taskList.size) {
                val context = LocalContext.current
                TaskCard(
                    modifier = Modifier
                        .combinedClickable(
                            indication = LocalIndication.current,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                if (states.taskListSelected.isNotEmpty()
                                ) {
                                    if (states.taskListSelected.contains(states.taskList[it])) {
                                        onClick(TaskEvents.RemoveTaskList(states.taskList[it]))
                                    } else {
                                        onClick(TaskEvents.AddTaskList(states.taskList[it]))
                                    }
                                } else {
                                    onClick(TaskEvents.SetCurrentTaskEdit(states.taskList[it]))
                                }
                            },
                            onLongClick = {
                                onClick(TaskEvents.AddTaskList(states.taskList[it]))
                            }
                        )
                        .background(
                            if (states.taskListSelected.contains(states.taskList[it]))
                                MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.2f
                                ) else Color.Transparent
                        ),
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