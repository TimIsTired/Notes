package com.timistired.notes.data.notes.local

import com.timistired.notes.data.model.Note
import com.timistired.notes.util.log.ILogger
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.ext.getFullName

class NotesLocalDataSource(
    private val dao: NotesDao,
    private val logger: ILogger
) : INotesLocalDataSource {

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
            .doOnComplete {
                logger.logMessage(
                    tag = this::class.getFullName(),
                    debugMessage = "Note successfully saved."
                )
            }
    }

    /**
     * Deletes one note from local database.
     *
     * @param id the ID of the note to delete
     * */
    override fun deleteNoteById(id: Long): Completable {
        return dao.deleteById(id)
            .doOnComplete {
                logger.logMessage(
                    tag = this::class.getFullName(),
                    debugMessage = "Note $id deleted."
                )
            }
    }
}