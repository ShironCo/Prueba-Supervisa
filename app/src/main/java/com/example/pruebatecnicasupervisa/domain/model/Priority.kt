package com.example.pruebatecnicasupervisa.domain.model

import androidx.compose.ui.graphics.Color

enum class Priority (
    val label: String,
    val selectedContainerColor: Color,
    val selectedLabelColor: Color
) {
    MEDIUM("Media", Color(0xFFFFE473), Color(0xFF292713)),
    HIGH("Alta", Color(0xFFFEB4B4), Color(0xFF940202)),
    LOW("Baja", Color(0xFFC8F5CD), Color(0xFF0B2E0D))
}