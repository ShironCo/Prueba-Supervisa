package com.example.pruebatecnicasupervisa.domain.model

import androidx.compose.ui.graphics.Color

enum class State (
    val label: String,
    val selectedContainerColor: Color,
    val selectedLabelColor: Color
) {
    PENDING("Pendiente", Color(0xFFEAEAE9), Color(0xFF323335)),
    IN_PROGRESS("En progreso", Color(0xFFD7E6F7), Color(0xFF7BAFEA)),
    COMPLETED("Completada", Color(0xFFC8F5CD), Color(0xFF0B2E0D))
}