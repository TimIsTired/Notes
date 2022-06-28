package com.timistired.notes.ui.detail.di

import com.timistired.notes.ui.detail.DetailViewModel
import org.koin.dsl.module

val detailModule = module {
    single { DetailViewModel(get(), get()) }
}