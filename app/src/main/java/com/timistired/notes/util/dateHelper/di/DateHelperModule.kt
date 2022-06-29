package com.timistired.notes.util.dateHelper.di

import com.timistired.notes.util.dateHelper.DateHelper
import com.timistired.notes.util.dateHelper.IDateHelper
import org.koin.dsl.module

val dateHelperModule = module {
    single<IDateHelper> { DateHelper() }
}