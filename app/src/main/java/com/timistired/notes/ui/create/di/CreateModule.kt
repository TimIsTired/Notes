package com.timistired.notes.ui.create.di

import com.timistired.notes.ui.create.CreateViewModel
import org.koin.dsl.module

val createModule = module {
    single { CreateViewModel(get(), get(), get()) }
}