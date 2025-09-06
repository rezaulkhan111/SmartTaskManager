package com.machinecode.smarttaskmanager.ui.screens

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.machinecode.smarttaskmanager.domain.Task
import com.machinecode.smarttaskmanager.ui.components.TaskItemCard


@Composable
fun HomeScreenContent(
    repositories: List<Task>,
    searchQuery: String,
    onTypeSearchQuery: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSortClick: () -> Unit,
    onRepoClick: (Task) -> Unit,
    listState: LazyListState
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
                onValueChange = onTypeSearchQuery,
                modifier = Modifier
                    .testTag("ttSearchInput")
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Search") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                modifier = Modifier.testTag("ttBtnSearch"), onClick = onSearchClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }

            IconButton(
                modifier = Modifier.testTag("ttBtnSort"), onClick = onSortClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.List,
                    contentDescription = "Sort",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }
        }

        if (repositories.isEmpty()) {
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
                items(repositories) { repo ->
                    TaskItemCard(repo, onClick = { onRepoClick(repo) })
                }
            }
        }
    }
}