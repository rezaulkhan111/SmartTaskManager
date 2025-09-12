package com.machinecode.smarttaskmanager.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.machinecode.smarttaskmanager.presentation.ui.TasksVM

@Composable
fun AddTaskScreen(
    onCancel: () -> Unit
) {
    val viewModel: TasksVM = hiltViewModel()
    val context = LocalContext.current

    AddTaskScreenContent(
        onCancel = onCancel,
        onSave = { mTitle, mCreateDate ->
            if (mTitle.isNotBlank()) {
                if (mCreateDate != null) {
                    viewModel.createWithDeadline(mTitle, mCreateDate)
                } else {
                    viewModel.createSimple(mTitle)
                }
                onCancel()
            }
        }
    )
}