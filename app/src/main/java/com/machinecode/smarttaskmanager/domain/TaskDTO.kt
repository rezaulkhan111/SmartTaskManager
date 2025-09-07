package com.machinecode.smarttaskmanager.domain

import java.time.LocalDateTime

sealed interface TaskState {
    fun start(): TaskState
    fun complete(): TaskState
    fun archive(): TaskState

    object Todo : TaskState {
        override fun start() = InProgress
        override fun complete() = this // not allowed directly
        override fun archive() = this // not allowed directly
    }

    object InProgress : TaskState {
        override fun start() = this
        override fun complete() = Done
        override fun archive() = this // not allowed directly
    }

    object Done : TaskState {
        override fun start() = this
        override fun complete() = this
        override fun archive() = Archived
    }

    object Archived : TaskState {
        override fun start() = this
        override fun complete() = this
        override fun archive() = this
    }
}

enum class Priority { LOW, MEDIUM, HIGH }
enum class Recurrence { NONE, DAILY, WEEKLY, MONTHLY }

data class TaskDTO(
    val id: String,
    val title: String,
    val description: String?,
    val due: LocalDateTime?,
    val priority: Priority,
    val tags: List<String>,
    val recurrence: Recurrence,
    val state: TaskState
)

// ----- Builder Pattern -----
class TaskBuilder(private val id: String, private val title: String) {
    private var description: String? = null
    private var due: LocalDateTime? = null
    private var priority: Priority = Priority.MEDIUM
    private var tags: MutableList<String> = mutableListOf()
    private var recurrence: Recurrence = Recurrence.NONE
    private var state: TaskState = TaskState.Todo

    fun description(value: String?) = apply { description = value }
    fun due(value: LocalDateTime?) = apply { due = value }
    fun priority(value: Priority) = apply { priority = value }
    fun addTag(tag: String) = apply { tags.add(tag) }
    fun recurrence(value: Recurrence) = apply { recurrence = value }
    fun state(value: TaskState) = apply { state = value }

    fun build(): TaskDTO = TaskDTO(
        id = id,
        title = title,
        description = description,
        due = due,
        priority = priority,
        tags = tags.toList(),
        recurrence = recurrence,
        state = state
    )
}

// ----- Strategy Pattern -----
interface TaskSortStrategy {
    fun sort(input: List<TaskDTO>): List<TaskDTO>
}

class SortByDeadline : TaskSortStrategy {
    override fun sort(input: List<TaskDTO>) = input.sortedWith(compareBy(nullsLast()) { it.due })
}

class SortByPriority : TaskSortStrategy {
    override fun sort(input: List<TaskDTO>) = input.sortedByDescending { it.priority }
}

class SortByTitle : TaskSortStrategy {
    override fun sort(input: List<TaskDTO>) = input.sortedBy { it.title.lowercase() }
}