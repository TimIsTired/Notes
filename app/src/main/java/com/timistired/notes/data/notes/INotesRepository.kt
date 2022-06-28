package com.timistired.notes.data.notes

import com.timistired.notes.data.model.Location
import com.timistired.notes.data.model.Note
import com.timistired.notes.data.model.NoteFull
import com.timistired.notes.data.model.NotePreview
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface INotesRepository {

    /**
     * Gets a list of all notes as [NotePreview], wrapped in an [Observable].
     * */
    fun getAllNotes(): Observable<List<NotePreview>>

    /**
     * Gets one note by ID.
     *
     * @param id the ID of the note of interest
     * */
    fun getNoteById(id: Long): Single<NoteFull>

    /**
     * Creates a note based on the provided values and saves it.
     *
     * @param header the header of the note
     * @param description the description of the note
     * @param location location to attach to the note (optional)
     * */
    fun createAndSaveNote(header: String, description: String, location: Location?): Completable

    /**
     * Deletes one note by ID.
     *
     * @param id the ID of the note to delete
     * */
    fun deleteNote(id: Long): Completable
}