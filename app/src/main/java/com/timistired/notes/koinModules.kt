package com.timistired.notes

import com.timistired.notes.data.notes.di.notesDataModule
import org.koin.core.module.Module

val koinModules: List<Module> = listOf(
    notesDataModule
)