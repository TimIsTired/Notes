package com.timistired.notes.util.sharedPreferences.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.timistired.notes.util.sharedPreferences.ISharedPrefHelper
import com.timistired.notes.util.sharedPreferences.SharedPrefHelper
import org.koin.dsl.module

val sharedPrefModule = module {
    single { provideSharedPreferences(get()) }
    single<ISharedPrefHelper> { SharedPrefHelper(get()) }
}

private fun provideSharedPreferences(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}