package com.machinecode.smarttaskmanager.domain.strategy

import com.machinecode.smarttaskmanager.domain.TaskDTO

class SortByDeadline : TaskSortStrategy {
    override val label: String = "By Deadline"

    override fun sort(input: List<TaskDTO>) = input.sortedWith(compareBy(nullsLast()) { it.due })
}