package com.machinecode.smarttaskmanager.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.machinecode.smarttaskmanager.presentation.ui.theme.SmartTaskManagerTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewListScreenContent() {
    SmartTaskManagerTheme {
//        AddTaskScreenContent(onCancel = {})
        HomeScreenContent()
    }
}