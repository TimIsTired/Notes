package com.timistired.notes.data.location.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.timistired.notes.data.location.ILocationRepository
import com.timistired.notes.data.location.LocationRepository
import com.timistired.notes.data.location.client.ILocationClient
import com.timistired.notes.data.location.client.LocationClient
import org.koin.dsl.module

val locationDataModule = module {
    single { provideFusedLocationClient(get()) }
    single<ILocationClient> { LocationClient(get(), get()) }
    single<ILocationRepository> { LocationRepository(get()) }
}

private fun provideFusedLocationClient(context: Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}