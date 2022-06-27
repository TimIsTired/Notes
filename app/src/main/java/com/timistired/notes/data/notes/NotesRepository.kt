package com.timistired.notes.data.notes

import com.timistired.notes.data.location.model.Location
import com.timistired.notes.data.notes.model.Note
import com.timistired.notes.data.notes.local.INotesLocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*

class NotesRepository(private val localDataSource: INotesLocalDataSource) : INotesRepository {
    /**
     * Gets a list of all notes as an [Observable].
     * */
    override fun getAllNotes(): Observable<List<Note>> {
        return localDataSource.getAllNotes()
    }

    /**
     * Gets one note by ID.
     *
     * @param id the ID of the note of interest
     * */
    override fun getNoteById(id: Long): Single<Note> {
        return localDataSource.getNoteById(id)
    }

    /**
     * Creates a note based on the provided values and saves it.
     *
     * @param header the header of the note
     * @param description the description of the note
     * @param location location to attach to the note (optional)
     * */
    override fun createAndSaveNote(
        header: String,
        description: String,
        location: Location
    ): Completable {
        val note = Note(
            header = header,
            description = description,
            location = location,
            creationDate = Date()
        )

        return localDataSource.saveNote(note)
    }

    /**
     * Deletes one note by ID.
     *
     * @param id the ID of the note to delete
     * */
    override fun deleteNote(id: Long): Completable {
        return localDataSource.deleteNoteById(id)
    }
}