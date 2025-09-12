package com.machinecode.smarttaskmanager.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}