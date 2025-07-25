package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar
import com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel.AddTaskEvents
import com.example.pruebatecnicasupervisa.presentation.viewModel.addTaskViewModel.AddTaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(title = "Nueva tarea", showIcon = true) {
                navHostController.popBackStack()
            }
        }
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
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = "Agregar tarea",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        AddTaskTextField(
            value = states.title,
            placeHolderText = "Título*",
            maxLengthTitle = 150,
            showIconError = true
        ) {
            viewModel.onEvent(AddTaskEvents.SetTitle(it))
        }
        AddTaskTextField(
            modifier = Modifier.height(200.dp),
            value = states.description,
            placeHolderText = "Descripción",
            maxLengthTitle = 1000,
            showIconError = false
        ) {
            viewModel.onEvent(AddTaskEvents.SetTitle(it))
        }

    }
}

@Composable
fun AddTaskTextField(
    modifier: Modifier = Modifier,
    value: String,
    showIconError: Boolean = false,
    placeHolderText: String,
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
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
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
