package com.timistired.notes.ui.overview.di

import com.timistired.notes.ui.overview.OverviewViewModel
import org.koin.dsl.module

val overviewModule = module {
    single { OverviewViewModel(get(), get(), get()) }
}