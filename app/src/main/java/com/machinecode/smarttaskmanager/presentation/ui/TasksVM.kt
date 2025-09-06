package com.machinecode.smarttaskmanager.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machinecode.smarttaskmanager.data.TaskRepository
import com.machinecode.smarttaskmanager.domain.SortByDeadline
import com.machinecode.smarttaskmanager.domain.Task
import com.machinecode.smarttaskmanager.domain.TaskFactory
import com.machinecode.smarttaskmanager.domain.TaskSortStrategy
import com.machinecode.smarttaskmanager.domain.TaskType
import com.machinecode.smarttaskmanager.integrations.CalendarClient
import com.machinecode.smarttaskmanager.notifications.NotificationFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TasksVM(
    private val repo: TaskRepository,
    private val calendar: CalendarClient,
    private val notifications: NotificationFactory
) : ViewModel() {

    // -------------------------
    // Strategy: current sort strategy (mutable at runtime)
    // -------------------------
    private val _sortStrategy = MutableStateFlow<TaskSortStrategy>(SortByDeadline())
    val sortStrategy: StateFlow<TaskSortStrategy> = _sortStrategy.asStateFlow()

    // -------------------------
    // Observer: tasks stream exposed to UI (repo.tasks is a StateFlow)
    // Combine with current sorting strategy and expose a sorted StateFlow
    // stateIn is used so Compose / UI can collect easily without launching a new coroutine.
    // -------------------------
    val tasks: StateFlow<List<Task>> = combine(repo.tasks, _sortStrategy) { list, sorter ->
        sorter.sort(list)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    /** Change the active sorting strategy (Strategy pattern). */
    fun setSortStrategy(strategy: TaskSortStrategy) {
        _sortStrategy.value = strategy
    }

    /** Create a simple task using TaskFactory (Factory Method). */
    fun createSimple(title: String) = viewModelScope.launch {
        if (title.isBlank()) return@launch
        val task = TaskFactory.create(TaskType.Simple(title))
        repo.add(task)
        notifications.createNotifier().notifyTask(task, "Created")
    }

    /** Create a task with a deadline (Factory Method + Abstract Factory for scheduling reminders). */
    fun createWithDeadline(title: String, due: LocalDateTime) = viewModelScope.launch {
        if (title.isBlank()) return@launch
        val task = TaskFactory.create(TaskType.WithDeadline(title, due))
        repo.add(task)
        // schedule reminder (Abstract Factory -> ReminderScheduler)
        notifications.createScheduler().schedule(task)
    }

    /** Transition a task to its next 'start' state (State pattern). */
    fun toInProgress(task: Task) = viewModelScope.launch {
        val nextState = task.state.start()
        val updated = task.copy(state = nextState)
        repo.update(updated)
    }

    /** Mark a task complete (State) and push to calendar (Adapter). */
    fun complete(task: Task) = viewModelScope.launch {
        val completedState = task.state.complete()
        val updated = task.copy(state = completedState)
        repo.update(updated)

        // Adapter: push to external calendar clients
        calendar.addEventFromTask(updated)

        // Notify user
        notifications.createNotifier().notifyTask(updated, "Completed")
    }

    /** Archive a task (State). */
    fun archive(task: Task) = viewModelScope.launch {
        val archivedState = task.state.archive()
        val updated = task.copy(state = archivedState)
        repo.update(updated)
    }

    /** Remove a task from the repository. */
    fun remove(task: Task) = viewModelScope.launch {
        repo.remove(task.id)
    }

    /** Generic update helper if you want to update arbitrary fields. */
    fun updateTask(updated: Task) = viewModelScope.launch {
        repo.update(updated)
    }
}