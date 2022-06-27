package com.timistired.notes.data.notes.database

import androidx.room.RoomDatabase
import com.timistired.notes.data.notes.model.Note
import com.timistired.notes.data.notes.local.NotesDao

@androidx.room.Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}