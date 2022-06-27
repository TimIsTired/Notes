package com.timistired.notes

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // koin setup
        startKoin {
            androidContext(this@NotesApplication)
            modules(koinModules)
        }
    }
}