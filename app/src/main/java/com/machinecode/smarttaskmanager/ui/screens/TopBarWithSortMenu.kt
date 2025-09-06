package com.machinecode.smarttaskmanager.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TopBarWithSortMenu(
    title: String, sortOptions: List<String>, onSortSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(sortOptions.first()) }

    TopAppBar(title = { Text(title) }, actions = {
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Sort"
                )
            }
            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false }) {
                sortOptions.forEach { option ->
                    DropdownMenuItem(text = { Text(option) }, onClick = {
                        selectedOption = option
                        onSortSelected(option)
                        expanded = false
                    })
                }
            }
        }
    })
}