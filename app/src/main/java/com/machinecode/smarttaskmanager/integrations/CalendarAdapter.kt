package com.machinecode.smarttaskmanager.integrations

import android.os.Build
import com.machinecode.smarttaskmanager.domain.TaskDTO
import java.time.ZoneId

interface CalendarClient {
    fun addEventFromTask(task: TaskDTO)
}

// Imagine these are third‑party SDK models
class GoogleEvent(val summary: String, val epochMillis: Long)
class OutlookItem(val subject: String, val timestamp: Long)

// Concrete adapters
class GoogleCalendarAdapter : CalendarClient {
    override fun addEventFromTask(task: TaskDTO) {
        val millis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            task.due?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                ?: System.currentTimeMillis()
        } else {
            task.due?.let {
                val sdf =
                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
                val date = sdf.parse(it.toString()) // or custom handling
                date?.time ?: System.currentTimeMillis()
            } ?: System.currentTimeMillis()
        }
        val event = GoogleEvent(summary = task.title, epochMillis = millis)
        println("[GoogleAdapter] Created GoogleEvent: ${'$'}event")
// TODO: call Google Calendar API
    }
}


class OutlookCalendarAdapter : CalendarClient {
    override fun addEventFromTask(task: TaskDTO) {
        val millis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            task.due?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                ?: System.currentTimeMillis()
        } else {
            task.due?.let {
                val sdf =
                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
                val date = sdf.parse(it.toString()) // or custom handling
                date?.time ?: System.currentTimeMillis()
            } ?: System.currentTimeMillis()
        }
        val item = OutlookItem(subject = task.title, timestamp = millis)
        println("[OutlookAdapter] Created OutlookItem: ${'$'}item")
// TODO: call Outlook API
    }
}