package com.timistired.notes.data.notes.di

import android.content.Context
import androidx.room.Room
import com.timistired.notes.data.notes.database.NotesDatabase
import com.timistired.notes.util.Constants
import org.koin.dsl.module

val notesDataModule = module {
    single { provideDatabase(get()) }
}

private fun provideDatabase(context: Context): NotesDatabase {
    val builder = Room.databaseBuilder(context, NotesDatabase::class.java, Constants.DB_NAME)
        .fallbackToDestructiveMigration()
    return builder.build()
}