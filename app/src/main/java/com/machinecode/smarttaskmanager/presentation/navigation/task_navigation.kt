package com.machinecode.smarttaskmanager.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machinecode.smarttaskmanager.presentation.ui.screens.AddTaskScreen
import com.machinecode.smarttaskmanager.presentation.ui.screens.AddTaskScreenContent
import com.machinecode.smarttaskmanager.presentation.ui.screens.HomeScreen

@Composable
@Preview(showSystemUi = true)
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "home_screen"
    ) {
        composable("home_screen") {
            HomeScreen(onAddTaskClick = {
                navController.navigate("addTask_screen")
            })
        }

        composable("addTask_screen") { backStackEntry ->
            AddTaskScreen(
                onCancel = {
                    navController.popBackStack()
                })
        }
    }
}