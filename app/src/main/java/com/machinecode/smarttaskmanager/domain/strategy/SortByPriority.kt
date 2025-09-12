package com.machinecode.smarttaskmanager.domain.strategy

import com.machinecode.smarttaskmanager.domain.TaskDTO

class SortByPriority : TaskSortStrategy {
    override val label = "By Priority"
    override fun sort(input: List<TaskDTO>) = input.sortedByDescending { it.priority }
}