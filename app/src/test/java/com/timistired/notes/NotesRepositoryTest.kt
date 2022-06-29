package com.timistired.notes

import com.timistired.notes.data.model.Location
import com.timistired.notes.data.notes.NotesRepository
import com.timistired.notes.data.notes.local.INotesLocalDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NotesRepositoryTest {

    @Test
    fun `getAllNotes fetches from local data source`() {
        val note = createTestNote()
        val localDataSource = mockk<INotesLocalDataSource> {
            every { getAllNotes() } returns Observable.just(listOf(note))
        }
        val notesRepository = NotesRepository(localDataSource)
        val testObservable = notesRepository.getAllNotes().test()

        testObservable.assertValue {
            val actual = it.first()
            actual.id == note.id && actual.header == note.header
        }
        testObservable.dispose()

        verify(exactly = 1) { localDataSource.getAllNotes() }
    }

    @Test
    fun `getNoteById fetches from local data source`() {
        val id = 9999L
        val note = createTestNote(id)
        val localDataSource = mockk<INotesLocalDataSource> {
            every { getNoteById(any()) } returns Single.just(note)
        }
        val notesRepository = NotesRepository(localDataSource)
        val testSingle = notesRepository.getNoteById(id).test()

        testSingle.assertValue(note)
        testSingle.dispose()

        verify(exactly = 1) { localDataSource.getNoteById(id) }
    }

    @Test
    fun `createAndSaveNote saves new note to local data source`() {
        val header = "Some header"
        val description = "Lorem ipsum dolor sit amet"
        val location = Location(52.277981, 10.550841)
        val localDataSource = mockk<INotesLocalDataSource> {
            every { saveNote(any()) } returns Completable.complete()
        }
        val notesRepository = NotesRepository(localDataSource)
        val testCompletable = notesRepository.createAndSaveNote(
            header = header,
            description = description,
            location = location
        ).test()

        testCompletable.assertComplete()
        testCompletable.dispose()

        verify(exactly = 1) {
            localDataSource.saveNote(withArg {
                it.header shouldBeEqualTo header
                it.description shouldBeEqualTo description
                it.location shouldBeEqualTo location
            })
        }
    }

    @Test
    fun `deleteNote deletes from local data source`() {
        val id = 2L
        val localDataSource = mockk<INotesLocalDataSource> {
            every { deleteNoteById(any()) } returns Completable.complete()
        }
        val notesRepository = NotesRepository(localDataSource)
        val testCompletable = notesRepository.deleteNote(id).test()

        testCompletable.assertComplete()
        testCompletable.dispose()

        verify(exactly = 1) { localDataSource.deleteNoteById(id) }
    }
}