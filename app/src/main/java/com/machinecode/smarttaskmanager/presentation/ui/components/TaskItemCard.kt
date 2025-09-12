package com.machinecode.smarttaskmanager.presentation.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.machinecode.smarttaskmanager.domain.TaskDTO
import com.machinecode.smarttaskmanager.presentation.ui.TasksVM
import java.time.format.DateTimeFormatter

@Composable
fun TaskItemCard(
    task: TaskDTO,
    onInProgress: (TaskDTO) -> Unit,
    onComplete: (TaskDTO) -> Unit,
    onArchive: (TaskDTO) -> Unit,
    onRemove: (TaskDTO) -> Unit,
    onClick: () -> Unit
) {
    val textColor = MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .testTag("ttRepositoryItem")
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = task.title, style = MaterialTheme.typography.titleMedium, color = textColor
            )
            if (!task.description.isNullOrEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
            }

            if (task.due != null) {
                val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")
                Text(
                    text = "Due: ${task.due.format(formatter)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onInProgress(task) }) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "In Progress",
                        tint = Color.Blue
                    )
                }

                IconButton(onClick = { onComplete(task) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Complete",
                        tint = Color.Green
                    )
                }

                IconButton(onClick = { onArchive(task) }) {
                    Icon(
                        imageVector = Icons.Default.Archive,
                        contentDescription = "Archive",
                        tint = Color.Gray
                    )
                }

                IconButton(onClick = { onRemove(task) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}