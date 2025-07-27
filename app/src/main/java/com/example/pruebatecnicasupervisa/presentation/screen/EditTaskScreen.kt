package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.domain.model.Task
import com.example.pruebatecnicasupervisa.presentation.ui.components.DatePickerFieldToModal
import com.example.pruebatecnicasupervisa.presentation.ui.components.FilterChip
import com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel.EditTaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.editTaskViewModel.EditTaskViewModel
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.taskViewModel.TaskViewModel

@Composable
fun EditTaskScreen(
    modifier : Modifier,
    task: Task,
    viewModel: EditTaskViewModel = hiltViewModel(),
) {
    val states by viewModel.states.collectAsState()
    val listPriority = listOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
    val listState = listOf(State.PENDING, State.IN_PROGRESS, State.COMPLETED)

    LaunchedEffect(Unit) {
        viewModel.onEvent(EditTaskEvents.SetId(task.task_id))
        viewModel.onEvent(EditTaskEvents.SetTitle(task.title))
        viewModel.onEvent(EditTaskEvents.SetDescription(task.description))
        viewModel.onEvent(EditTaskEvents.SetDueDate(task.due_Date))
        viewModel.onEvent(EditTaskEvents.SetPriority(task.priority))
        viewModel.onEvent(EditTaskEvents.SetState(task.status))
    }

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(states.snackBarMessage) {
        if (states.snackBarMessage.isNotBlank()) {
            snackBarHostState.showSnackbar(states.snackBarMessage)
            viewModel.onEvent(EditTaskEvents.ClearSnackBarMessage)
        }
    }

    Surface(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
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
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SnackbarHost(hostState = snackBarHostState){
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}