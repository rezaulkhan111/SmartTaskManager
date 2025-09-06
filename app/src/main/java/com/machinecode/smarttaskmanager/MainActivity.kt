package com.machinecode.smarttaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.machinecode.smarttaskmanager.presentation.navigation.NavHostScreen
import com.machinecode.smarttaskmanager.presentation.ui.theme.SmartTaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartTaskManagerTheme {
                NavHostScreen()
            }
        }
    }
}