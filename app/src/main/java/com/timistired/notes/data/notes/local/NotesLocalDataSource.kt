package com.timistired.notes.data.notes.local

import com.timistired.notes.data.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class NotesLocalDataSource(private val dao: NotesDao) : INotesLocalDataSource {

    /**
     * Gets a list of all notes from local database as an [Observable].
     * */
    override fun getAllNotes(): Observable<List<Note>> {
        return dao.getAll()
    }

    /**
     * Gets one note from local database.
     *
     * @param id the ID of the note of interest
     * */
    override fun getNoteById(id: Long): Single<Note> {
        return dao.getById(id)
    }

    /**
     * Saves one note to local database.
     *
     * @param note the note to persist
     * */
    override fun saveNote(note: Note): Completable {
        return dao.insert(note)
    }

    /**
     * Deletes one note from local database.
     *
     * @param id the ID of the note to delete
     * */
    override fun deleteNoteById(id: Long): Completable {
        return dao.deleteById(id)
    }
}