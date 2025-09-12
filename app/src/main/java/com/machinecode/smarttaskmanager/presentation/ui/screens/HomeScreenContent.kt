package com.machinecode.smarttaskmanager.presentation.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.machinecode.smarttaskmanager.presentation.ui.TasksVM
import com.machinecode.smarttaskmanager.presentation.ui.components.TaskItemCard


@Composable
fun HomeScreenContent(
    onAddTaskClick: () -> Unit,
) {
    val viewModel: TasksVM = hiltViewModel()

    val tasksState by viewModel.lsTask.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortStrategies = viewModel.sortStrategies
    val selectedStrategy by viewModel.selectedStrategy.collectAsState()

    val listState = rememberLazyListState()
    var showSortMenu by remember { mutableStateOf(false) }
//    var typedQuery by remember { mutableStateOf(searchQuery) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { itOnWord ->
                        viewModel.updateSearchQuery(itOnWord)
                    },
                    modifier = Modifier
                        .testTag("ttSearchInput")
                        .weight(1f)
                        .height(56.dp),
                    placeholder = { Text(text = "Search") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Box {
                    IconButton(
                        modifier = Modifier
                            .testTag("ttBtnSort")
                            .padding(10.dp),
                        onClick = { showSortMenu = true } /*onSortClick*/) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.List,
                            contentDescription = "Sort",
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = showSortMenu, onDismissRequest = { showSortMenu = false }) {
                        sortStrategies.forEach { strategy ->
                            DropdownMenuItem(text = { Text(strategy.label) }, onClick = {
                                viewModel.setSortStrategy(strategy)
                                showSortMenu = false
                            }, leadingIcon = {
                                if (strategy == selectedStrategy) {
                                    Icon(Icons.Default.Check, contentDescription = "Selected")
                                }
                            })
                        }
                    }
                }
            }

            if (tasksState.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No repositories found",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.testTag("ttEmptyStateText")
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.testTag("ttLcRepository"),
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tasksState) { repo ->
                        TaskItemCard(
                            repo,
                            onInProgress = { viewModel.toInProgress(it) },
                            onComplete = { viewModel.complete(it) },
                            onArchive = { viewModel.archive(it) },
                            onRemove = { viewModel.remove(it) },
                            onClick = {
                                println("Home: " + repo.title)
                            })
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { onAddTaskClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Add Task"
            )
        }
    }
}