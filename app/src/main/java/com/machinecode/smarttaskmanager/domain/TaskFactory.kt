package com.machinecode.smarttaskmanager.domain

import java.time.LocalDateTime
import java.util.UUID

sealed class TaskType {
    data class Simple(val title: String) : TaskType()
    data class WithDeadline(val title: String, val due: LocalDateTime) : TaskType()
    data class Recurring(val title: String, val recurrence: Recurrence) : TaskType()
}

object TaskFactory {

    // Factory Method
    fun create(type: TaskType): TaskDTO = when (type) {
        is TaskType.Simple -> TaskBuilder(id(), type.title).build()
        is TaskType.WithDeadline -> TaskBuilder(id(), type.title).due(type.due).build()
        is TaskType.Recurring -> TaskBuilder(id(), type.title).recurrence(type.recurrence).build()
    }

    private fun id() = UUID.randomUUID().toString()
}