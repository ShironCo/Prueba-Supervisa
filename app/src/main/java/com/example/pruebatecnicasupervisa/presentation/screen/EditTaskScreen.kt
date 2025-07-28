package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task
import com.example.pruebatecnicasupervisa.presentation.ui.components.DatePickerFieldToModal
import com.example.pruebatecnicasupervisa.presentation.ui.components.FilterChip
import com.example.pruebatecnicasupervisa.presentation.ui.components.TaskCard
import com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel.EditTaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel.EditTaskViewModel
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskViewModel
import kotlinx.coroutines.delay

@Composable
fun EditTaskScreen(
    modifier: Modifier,
    task: Task,
    viewModelTask: TaskViewModel,
    viewModel: EditTaskViewModel = hiltViewModel(),
) {
    val states by viewModel.states.collectAsState()
    val listPriority = listOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
    val listState = listOf(State.PENDING, State.IN_PROGRESS, State.COMPLETED)
    var alertDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        viewModel.onEvent(EditTaskEvents.SetIsTaskDelete(false))
        viewModel.onEvent(EditTaskEvents.SetId(task.task_id))
        viewModel.onEvent(EditTaskEvents.SetTitle(task.title))
        viewModel.onEvent(EditTaskEvents.SetDescription(task.description))
        viewModel.onEvent(EditTaskEvents.SetDueDate(task.due_Date))
        viewModel.onEvent(EditTaskEvents.SetPriority(task.priority))
        viewModel.onEvent(EditTaskEvents.SetState(task.status))
    }

    LaunchedEffect(states.snackBarMessage) {
        if (states.snackBarMessage.isNotBlank()) {
            delay(500)
            viewModel.onEvent(EditTaskEvents.ClearSnackBarMessage)
            viewModelTask.onEvent(TaskEvents.SetCurrentTaskEdit(null))
        }
    }

    LaunchedEffect(states.isTaskDelete) {
        if (states.isTaskDelete){
            viewModelTask.onEvent(TaskEvents.SetCurrentTaskEdit(null))
        }
    }

    if (alertDialog) {
        AlertDialog(
            onDismissRequest = { alertDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onEvent(EditTaskEvents.DeleteTask)
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
                    text = "¿Seguro deseas eliminar la tarea?",
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
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            item {
                Column {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Previzualicación",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    TaskCard(
                        title = states.title ?: task.title,
                        description = states.description ?: task.description ?: "",
                        dueDate = states.dueDate ?: task.due_Date,
                        priority = states.priority ?: task.priority,
                        state = states.status ?: task.status
                    ) {
                    }
                }
            }
            item {
                AddTaskTextField(
                    value = states.title ?: task.title,
                    placeHolderText = "Título*",
                    maxLengthTitle = 150,
                    showIconError = states.errorMessage == "El título es obligatorio",
                    isError = states.errorMessage == "title",
                    errorMessage = "El título es obligatorio"
                ) {
                    if (it.length <= 150) {
                        viewModel.onEvent(EditTaskEvents.SetTitle(it))
                    }
                }
                AddTaskTextField(
                    modifier = Modifier.height(200.dp),
                    value = states.description ?: task.description ?: "",
                    placeHolderText = "Descripción",
                    maxLengthTitle = 1000,
                    imeAction = ImeAction.Default
                ) {
                    if (it.length <= 1000) {
                        viewModel.onEvent(EditTaskEvents.SetDescription(it))
                    }
                }
                DatePickerFieldToModal(value = states.dueDate ?: task.due_Date) {
                    viewModel.onEvent(EditTaskEvents.SetDueDate(it))
                }
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Prioridad*",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listPriority.forEach {
                        FilterChip(
                            label = it.label,
                            selectedContainerColor = it.selectedContainerColor,
                            selectedLabelColor = it.selectedLabelColor,
                            selected = it.label == (states.priority?.label ?: task.priority.label)
                        ) {
                            viewModel.onEvent(EditTaskEvents.SetPriority(it))
                        }
                    }
                }
                if (states.errorMessage == "priority") {
                    ValidateForm(text = "Selecciona una prioridad")
                }
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Estado*",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listState.forEach {
                        FilterChip(
                            label = it.label,
                            selectedContainerColor = it.selectedContainerColor,
                            selectedLabelColor = it.selectedLabelColor,
                            selected = it.label == (states.status?.label ?: task.status.label)
                        ) {
                            viewModel.onEvent(EditTaskEvents.SetState(it))
                        }
                    }
                }
                if (states.errorMessage == "status") {
                    ValidateForm(text = "Selecciona el estado inicial")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            alertDialog = true
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Eliminar",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.onEvent(EditTaskEvents.SaveTask)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.surface,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = "Editar tarea",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(visible = states.snackBarMessage.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 10.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = states.snackBarMessage,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 30.sp,
                )
            }
        }
    }
}