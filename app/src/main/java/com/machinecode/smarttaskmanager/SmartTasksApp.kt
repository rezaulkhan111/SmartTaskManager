package com.machinecode.smarttaskmanager

import android.app.Application
import com.machinecode.smarttaskmanager.di.ApplicationComponent
import com.machinecode.smarttaskmanager.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartTasksApp : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}