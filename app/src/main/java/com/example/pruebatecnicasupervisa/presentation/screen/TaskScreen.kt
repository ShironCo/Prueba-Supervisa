package com.example.pruebatecnicasupervisa.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pruebatecnicasupervisa.presentation.ui.components.TopBar

@Composable
fun TaskScreen(){
    Scaffold(
        topBar = { TopBar(title = "Gestion de tareas", showIcon = false) {

        }}
    ) {
        TaskBody(modifier = Modifier.padding(it))
    }
}

@Composable
fun TaskBody(modifier: Modifier){
    Surface(
        modifier = modifier.fillMaxSize()
    ){
        Column {
            Text(text = "Gestiona tus tareas de forma eficiente y organizada")
        }
    }

}