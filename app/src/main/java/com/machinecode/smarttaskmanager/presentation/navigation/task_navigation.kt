package com.machinecode.smarttaskmanager.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machinecode.smarttaskmanager.presentation.ui.screens.HomeScreen

@Composable
@Preview(showSystemUi = true)
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_screen"
    ) {
        composable("home_screen") {
            HomeScreen(onRepoClick = {})
        }

       /* composable("details_screen/{repoId}") { backStackEntry ->
            val repoId = backStackEntry.arguments?.getString("repoId")
            Log.e("MainAc", "NavHostScreen: " + Gson().toJson(repoId))
            DetailsScreen(repoId = repoId)
        }*/
    }
}