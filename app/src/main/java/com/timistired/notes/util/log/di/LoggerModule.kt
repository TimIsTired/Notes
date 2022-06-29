package com.timistired.notes.util.log.di

import com.timistired.notes.util.log.ILogger
import com.timistired.notes.util.log.SimpleLogger
import org.koin.dsl.module

val loggerModule = module {
    single<ILogger> { SimpleLogger() }
}