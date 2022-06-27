package com.timistired.notes.data.notes.di

import android.content.Context
import androidx.room.Room
import com.timistired.notes.data.notes.NotesRepository
import com.timistired.notes.data.notes.database.NotesDatabase
import com.timistired.notes.data.notes.local.NotesDao
import com.timistired.notes.data.notes.local.NotesLocalDataSource
import com.timistired.notes.util.Constants
import org.koin.dsl.module

val notesDataModule = module {
    single { provideDatabase(get()) }
    single { provideNotesDao(get()) }
    single { NotesLocalDataSource(get()) }
    single { NotesRepository(get()) }
}

private fun provideDatabase(context: Context): NotesDatabase {
    val builder = Room.databaseBuilder(context, NotesDatabase::class.java, Constants.DB_NAME)
        .fallbackToDestructiveMigration()
    return builder.build()
}

private fun provideNotesDao(database: NotesDatabase): NotesDao {
    return database.notesDao()
}