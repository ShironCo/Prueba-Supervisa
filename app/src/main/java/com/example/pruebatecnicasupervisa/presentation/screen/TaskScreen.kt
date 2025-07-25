package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar
import com.example.pruebatecnicasupervisa.presentation.ui.navigation.NavigationRoutes

@Composable
fun TaskScreen(
    navigationHostController: NavHostController
) {
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
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Tarea")
            }
        }
    ) {
        TaskBody(modifier = Modifier.padding(it))
    }
}

@Composable
fun TaskBody(modifier: Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column {

        }
    }

}