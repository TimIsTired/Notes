package com.timistired.notes

import com.timistired.notes.data.location.di.locationDataModule
import com.timistired.notes.data.notes.di.notesDataModule
import com.timistired.notes.ui.overview.di.overviewModule
import com.timistired.notes.util.log.di.loggerModule
import com.timistired.notes.util.sharedPreferences.di.sharedPrefModule
import org.koin.core.module.Module

val koinModules: List<Module> = listOf(
    notesDataModule,
    locationDataModule,
    overviewModule,
    sharedPrefModule,
    loggerModule
)