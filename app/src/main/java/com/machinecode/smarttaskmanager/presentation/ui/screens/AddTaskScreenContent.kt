package com.machinecode.smarttaskmanager.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreenContent(
    onSave: (String, LocalDateTime?) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var dueDate by remember { mutableStateOf<LocalDateTime?>(null) }

    val focusManager = LocalFocusManager.current
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, top = 60.dp, end = 10.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedButton(onClick = { showDatePicker = true }) {
            val formattedDate = dueDate?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            Text(text = formattedDate ?: "Select Due Date (Optional)")
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        dueDate = datePickerState.selectedDateMillis?.let {
                            Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                        }
                        showDatePicker = false
                    }) { Text("OK") }
                }, dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }) {
                DatePicker(
                    state = datePickerState
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) { Text("Cancel") }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onSave(title, dueDate)
                }, enabled = title.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}