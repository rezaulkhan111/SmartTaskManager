package com.machinecode.smarttaskmanager.notifications

import com.machinecode.smarttaskmanager.domain.TaskDTO


interface Notifier {
    fun notifyTask(task: TaskDTO, message: String)
}

interface ReminderScheduler {
    fun schedule(task: TaskDTO)
}

interface NotificationFactory { // Abstract Factory
    fun createNotifier(): Notifier
    fun createScheduler(): ReminderScheduler
}

class AndroidNotificationFactory : NotificationFactory {
    override fun createNotifier(): Notifier = AndroidNotifier()
    override fun createScheduler(): ReminderScheduler = AndroidReminderScheduler()
}

class AndroidNotifier : Notifier {
    override fun notifyTask(task: TaskDTO, message: String) {
// TODO: Hook into NotificationManager
        println("[AndroidNotifier] ${'$'}{task.title}: ${'$'}message")
    }
}


class AndroidReminderScheduler : ReminderScheduler {
    override fun schedule(task: TaskDTO) {
// TODO: Use WorkManager for exact scheduling
        println("[AndroidReminder] Scheduled reminder for ${'$'}{task.title}")
    }
}

class NoopNotificationFactory : NotificationFactory {
    override fun createNotifier(): Notifier = object : Notifier {
        override fun notifyTask(task: TaskDTO, message: String) { /* no-op */
        }
    }

    override fun createScheduler(): ReminderScheduler = object : ReminderScheduler {
        override fun schedule(task: TaskDTO) { /* no-op */
        }
    }
}