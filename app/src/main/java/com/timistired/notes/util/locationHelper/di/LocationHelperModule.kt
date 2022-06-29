package com.timistired.notes.util.locationHelper.di

import com.timistired.notes.util.locationHelper.ILocationHelper
import com.timistired.notes.util.locationHelper.LocationHelper
import org.koin.dsl.module

val locationHelperModule = module {
    single<ILocationHelper> { LocationHelper(get()) }
}