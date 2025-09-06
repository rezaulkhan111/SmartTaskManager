package com.machinecode.smarttaskmanager.data

import com.machinecode.smarttaskmanager.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>> // Observer: consumers subscribe to changes
    suspend fun add(task: Task)
    suspend fun update(task: Task)
    suspend fun remove(id: String)
}

class InMemoryTaskRepository : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun add(task: Task) {
        _tasks.value = _tasks.value + task
    }

    override suspend fun update(task: Task) {
        _tasks.value = _tasks.value.map { if (it.id == task.id) task else it }
    }

    override suspend fun remove(id: String) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }
}

// ----- Decorators -----
class LoggingRepository(private val inner: TaskRepository) : TaskRepository {
    override val tasks: StateFlow<List<Task>> get() = inner.tasks

    override suspend fun add(task: Task) {
        println("[RepoLog] add ${'$'}task"); inner.add(task)
    }

    override suspend fun update(task: Task) {
        println("[RepoLog] update ${'$'}task"); inner.update(task)
    }

    override suspend fun remove(id: String) {
        println("[RepoLog] remove ${'$'}id"); inner.remove(id)
    }
}

class CachingRepository(private val inner: TaskRepository) : TaskRepository {
    // For demo, tasks flow already caches state. In real apps, you could memoize filters, etc.
    override val tasks: StateFlow<List<Task>> get() = inner.tasks
    override suspend fun add(task: Task) = inner.add(task)
    override suspend fun update(task: Task) = inner.update(task)
    override suspend fun remove(id: String) = inner.remove(id)
}