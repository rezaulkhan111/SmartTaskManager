package com.machinecode.smarttaskmanager.domain.strategy

import com.machinecode.smarttaskmanager.domain.TaskDTO

// ----- Strategy Pattern -----
interface TaskSortStrategy {
    val label: String
    fun sort(input: List<TaskDTO>): List<TaskDTO>
}