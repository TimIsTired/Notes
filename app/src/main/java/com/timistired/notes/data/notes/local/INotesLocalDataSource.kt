package com.timistired.notes.data.notes.local

import com.timistired.notes.data.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface INotesLocalDataSource {

    /**
     * Gets a list of all notes from local database as an [Observable].
     * */
    fun getAllNotes(): Observable<List<Note>>

    /**
     * Gets one note from local database.
     *
     * @param id the ID of the note of interest
     * */
    fun getNoteById(id: Long): Single<Note>

    /**
     * Saves one note to local database.
     *
     * @param note the note to persist
     * */
    fun saveNote(note: Note): Completable

    /**
     * Deletes one note from local database.
     *
     * @param id the ID of the note to delete
     * */
    fun deleteNoteById(id: Long): Completable
}