package com.machinecode.smarttaskmanager.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machinecode.smarttaskmanager.data.TaskRepository
import com.machinecode.smarttaskmanager.domain.TaskDTO
import com.machinecode.smarttaskmanager.domain.TaskFactory
import com.machinecode.smarttaskmanager.domain.TaskType
import com.machinecode.smarttaskmanager.domain.strategy.SortByDeadline
import com.machinecode.smarttaskmanager.domain.strategy.SortByPriority
import com.machinecode.smarttaskmanager.domain.strategy.SortByTitle
import com.machinecode.smarttaskmanager.domain.strategy.TaskSortStrategy
import com.machinecode.smarttaskmanager.integrations.CalendarClient
import com.machinecode.smarttaskmanager.notifications.NotificationFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TasksVM @Inject constructor(
    private val repo: TaskRepository,
    private val calendar: CalendarClient,
    private val notifications: NotificationFactory
) : ViewModel() {

    val sortStrategies: List<TaskSortStrategy> = listOf(
        SortByDeadline(), SortByPriority(), SortByTitle()
    )

    // -------------------------
    // Strategy: current sort strategy (mutable at runtime)
    // -------------------------
    private val _sortStrategy = MutableStateFlow<TaskSortStrategy>(SortByDeadline())
    val selectedStrategy: StateFlow<TaskSortStrategy> = _sortStrategy.asStateFlow()

    // -----------------------------
    // Search Query State
    // -----------------------------
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // -------------------------
    // Observer: tasks stream exposed to UI (repo.tasks is a StateFlow)
    // Combine with current sorting strategy and expose a sorted StateFlow
    // stateIn is used so Compose / UI can collect easily without launching a new coroutine.
    // -------------------------
    val lsTask: StateFlow<List<TaskDTO>> =
        combine(repo.tasks, _sortStrategy, _searchQuery) { lsTask, sorter, searchQuery ->
            val filtered = if (searchQuery.isBlank()) lsTask
            else lsTask.filter { it.title.contains(searchQuery, ignoreCase = true) }

            sorter.sort(input = filtered)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

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
    fun toInProgress(task: TaskDTO) = viewModelScope.launch {
        val nextState = task.state.start()
        val updated = task.copy(state = nextState)
        repo.update(updated)
    }

    /** Mark a task complete (State) and push to calendar (Adapter). */
    fun complete(task: TaskDTO) = viewModelScope.launch {
        val completedState = task.state.complete()
        val updated = task.copy(state = completedState)
        repo.update(updated)

        // Adapter: push to external calendar clients
        calendar.addEventFromTask(updated)

        // Notify user
        notifications.createNotifier().notifyTask(updated, "Completed")
    }

    /** Archive a task (State). */
    fun archive(task: TaskDTO) = viewModelScope.launch {
        val archivedState = task.state.archive()
        val updated = task.copy(state = archivedState)
        repo.update(updated)
    }

    /** Remove a task from the repository. */
    fun remove(task: TaskDTO) = viewModelScope.launch {
        repo.remove(task.id)
    }

    /** Generic update helper if you want to update arbitrary fields. */
    fun updateTask(updated: TaskDTO) = viewModelScope.launch {
        repo.update(updated)
    }
}