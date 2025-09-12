package com.machinecode.smarttaskmanager.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreen(
    onAddTaskClick: () -> Unit
) {
    val context = LocalContext.current

    HomeScreenContent(
        onAddTaskClick = onAddTaskClick,
    )
}