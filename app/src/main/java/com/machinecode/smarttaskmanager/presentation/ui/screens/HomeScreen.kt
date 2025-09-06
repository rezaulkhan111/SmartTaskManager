package com.machinecode.smarttaskmanager.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.machinecode.smarttaskmanager.domain.*

@Composable
fun HomeScreen(
    onRepoClick: (Task) -> Unit
) {

    HomeScreenContent(
        repositories = List<Task>,
        searchQuery = "",
        onTypeSearchQuery = null,
        onSearchClick = null,
        onSortClick = null,
        onRepoClick = onRepoClick,
        listState = null
    )
}