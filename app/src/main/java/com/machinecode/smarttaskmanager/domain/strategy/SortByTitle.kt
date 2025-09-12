package com.machinecode.smarttaskmanager.domain.strategy

import com.machinecode.smarttaskmanager.domain.TaskDTO

class SortByTitle : TaskSortStrategy {
    override val label = "By Title"
    override fun sort(input: List<TaskDTO>) = input.sortedBy { it.title.lowercase() }
}