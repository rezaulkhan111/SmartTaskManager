package com.machinecode.smarttaskmanager.di

import com.machinecode.smarttaskmanager.data.InMemoryTaskRepository
import com.machinecode.smarttaskmanager.data.TaskRepository
import com.machinecode.smarttaskmanager.integrations.CalendarClient
import com.machinecode.smarttaskmanager.integrations.GoogleCalendarAdapter
import com.machinecode.smarttaskmanager.notifications.AndroidNotificationFactory
import com.machinecode.smarttaskmanager.notifications.NotificationFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRepository(): TaskRepository = InMemoryTaskRepository()

    @Provides
    @Singleton
    fun provideCalendar(): CalendarClient = GoogleCalendarAdapter()

    @Provides
    @Singleton
    fun provideNotifications(): NotificationFactory = AndroidNotificationFactory()
}