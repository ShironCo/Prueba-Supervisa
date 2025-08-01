package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State
import com.example.pruebatecnicasupervisa.presentation.ui.components.DatePickerFieldToModal
import com.example.pruebatecnicasupervisa.presentation.ui.components.FilterChip
import com.example.pruebatecnicasupervisa.presentation.ui.components.TaskCard
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar
import com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel.AddTaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel.AddTaskViewModel

@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val states by viewModel.states.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(states.snackBarMessage) {
        if (states.snackBarMessage.isNotBlank()) {
            snackBarHostState.showSnackbar(states.snackBarMessage)
            viewModel.onEvent(AddTaskEvents.ClearSnackBarMessage)
        }
    }
    Scaffold(
        topBar = {
            TopBar(title = "Nueva tarea", showIcon = true) {
                navHostController.popBackStack()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState){
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            }
        },
    ) {
        AddTaskBody(modifier = Modifier.padding(it), viewModel = viewModel)
    }

}

@Composable
fun AddTaskBody(
    modifier: Modifier,
    viewModel: AddTaskViewModel
) {
    Surface(
        modifier.fillMaxSize()
    ) {
        Column {
            AddTaskForm(viewModel = viewModel)
        }
    }
}

@Composable
fun AddTaskForm(
    viewModel: AddTaskViewModel
) {
    val states by viewModel.states.collectAsState()
    val listPriority = listOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW)
    val listState = listOf(State.PENDING, State.IN_PROGRESS, State.COMPLETED)
    val pagerState = rememberPagerState { states.taskList.size }
    LaunchedEffect(states.taskList.size) {
        if (states.taskList.isNotEmpty()) {
            pagerState.animateScrollToPage(states.taskList.lastIndex)
        }
    }
    LazyColumn {
        item {
            AnimatedVisibility(visible = states.taskList.isNotEmpty()) {
                HorizontalPager(state = pagerState) { index ->
                    TaskCard(
                        title = states.taskList[index].title,
                        description = states.taskList[index].description,
                        dueDate = states.taskList[index].due_Date,
                        priority = states.taskList[index].priority,
                        state = states.taskList[index].status,
                        maxLines = 4
                    ) {
                        IconButton(onClick = {
                            viewModel.onEvent(AddTaskEvents.DeleteTask(states.taskList[index]))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar tarea"
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.onEvent(AddTaskEvents.SaveTask(states.taskList[index]))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Guardar tarea"
                            )
                            Text(
                                text = "Guardar tarea",
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "Agregar tarea",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                AddTaskTextField(
                    value = states.title,
                    placeHolderText = "Título*",
                    maxLengthTitle = 150,
                    showIconError = states.errorMessage == "El título es obligatorio",
                    isError = states.errorMessage == "title",
                    errorMessage = "El título es obligatorio"
                ) {
                    if (it.length <= 150) {
                        viewModel.onEvent(AddTaskEvents.SetTitle(it))
                    }
                }
                AddTaskTextField(
                    modifier = Modifier.height(200.dp),
                    value = states.description,
                    placeHolderText = "Descripción",
                    maxLengthTitle = 1000,
                    imeAction = ImeAction.Default
                ) {
                    if (it.length <= 1000) {
                        viewModel.onEvent(AddTaskEvents.SetDescription(it))
                    }
                }
                DatePickerFieldToModal(value = states.dueDate) {
                    viewModel.onEvent(AddTaskEvents.SetDueDate(it))
                }
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Prioridad*",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
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
                            selected = it.label == states.priority?.label
                        ) {
                            viewModel.onEvent(AddTaskEvents.SetPriority(it))
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
                    color = MaterialTheme.colorScheme.onSecondary
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
                            selected = it.label == states.status?.label
                        ) {
                            viewModel.onEvent(AddTaskEvents.SetState(it))
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
                            viewModel.onEvent(AddTaskEvents.AddTask)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.surface,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = "Crear tarea",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ValidateForm(
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun AddTaskTextField(
    modifier: Modifier = Modifier,
    value: String,
    showIconError: Boolean = false,
    placeHolderText: String,
    errorMessage: String = "",
    isError: Boolean = false,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    maxLengthTitle: Int = 0,
    onValueChange: (String) -> Unit
) {
    val focus = LocalFocusManager.current
    Column {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            ),
            shape = MaterialTheme.shapes.medium,
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingIcon = {
                if (showIconError)
                    Icon(
                        imageVector = Icons.Default.PriorityHigh,
                        contentDescription = "Título obligatorio",
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
            keyboardActions = KeyboardActions(
                onNext = {
                    focus.moveFocus(FocusDirection.Next)
                }
            ),
            singleLine = singleLine,
            isError = isError
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isError){
                ValidateForm(text = errorMessage)
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${value.length}/ $maxLengthTitle",
                style = MaterialTheme.typography.labelLarge,
                color = if (value.length == maxLengthTitle) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                },
                textAlign = TextAlign.Right
            )
        }

    }
}

