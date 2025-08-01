package com.example.pruebatecnicasupervisa.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicasupervisa.domain.model.Priority
import com.example.pruebatecnicasupervisa.domain.model.State

@Composable
fun TaskCard(
    modifier : Modifier = Modifier,
    title: String,
    description: String?,
    dueDate: Long?,
    priority: Priority,
    state: State,
    maxLines: Int = Int.MAX_VALUE,
    content: @Composable RowScope.() -> Unit
){
    Card(
        modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = modifier.padding(15.dp)
        ) {
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                maxLines = maxLines,
                overflow = if (maxLines>0) TextOverflow.Ellipsis else TextOverflow.Clip
            )
            dueDate?.let {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Fecha de vencimiento",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                    )
                    Text(
                        modifier = Modifier,
                        text = convertMillisToDate(it),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
            Row {
                FilterChip(
                    label = priority.label,
                    selected = true,
                    enable = false,
                    selectedLabelColor = priority.selectedLabelColor,
                    selectedContainerColor = priority.selectedContainerColor
                ) {}
                Spacer(modifier = Modifier.width(10.dp))
                FilterChip(
                    label = state.label,
                    selected = true,
                    enable = false,
                    selectedLabelColor = state.selectedLabelColor,
                    selectedContainerColor = state.selectedContainerColor
                ) {}
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                content = content)
        }
    }
}
