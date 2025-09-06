package com.machinecode.smarttaskmanager.notifications

import com.machinecode.smarttaskmanager.domain.Task

interface Notifier {
    fun notifyTask(task: Task, message: String)
}

interface ReminderScheduler {
    fun schedule(task: Task)
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
    override fun notifyTask(task: Task, message: String) {
// TODO: Hook into NotificationManager
        println("[AndroidNotifier] ${'$'}{task.title}: ${'$'}message")
    }
}


class AndroidReminderScheduler : ReminderScheduler {
    override fun schedule(task: Task) {
// TODO: Use WorkManager for exact scheduling
        println("[AndroidReminder] Scheduled reminder for ${'$'}{task.title}")
    }
}

class NoopNotificationFactory : NotificationFactory {
    override fun createNotifier(): Notifier = object : Notifier {
        override fun notifyTask(task: Task, message: String) { /* no-op */
        }
    }

    override fun createScheduler(): ReminderScheduler = object : ReminderScheduler {
        override fun schedule(task: Task) { /* no-op */
        }
    }
}