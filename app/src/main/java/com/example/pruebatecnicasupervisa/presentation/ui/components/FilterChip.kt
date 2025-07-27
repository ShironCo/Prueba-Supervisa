package com.example.pruebatecnicasupervisa.presentation.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FilterChip(
    label: String,
    selected : Boolean = false,
    enable : Boolean = true,
    selectedContainerColor: Color = Color.Transparent,
    selectedLabelColor: Color = Color.Transparent,
    onClick : () -> Unit
) {
    FilterChip(
        onClick = { onClick() },
        label = {
            Text(label)
        },
        selected = selected,
        enabled = enable,
        colors = FilterChipDefaults.filterChipColors(
            labelColor = Color.Black,
            selectedContainerColor = selectedContainerColor,
            selectedLabelColor = selectedLabelColor,
            disabledSelectedContainerColor = selectedContainerColor,
            disabledLabelColor = selectedLabelColor,
        )
    )
}
