package com.timistired.notes

import com.timistired.notes.data.notes.local.NotesDao
import com.timistired.notes.data.notes.local.NotesLocalDataSource
import com.timistired.notes.util.log.ILogger
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class NotesLocalDataSourceTest {

    private val mockLogger = mockk<ILogger> {
        every { logMessage(any(), any()) } just Runs
    }

    @Test
    fun `getAllNotes fetches from local database`() {
        val notes = listOf(createTestNote(), createTestNote(), createTestNote())
        val dao = mockk<NotesDao> {
            every { getAll() } returns Observable.just(notes)
        }
        val notesLocalDataSource = NotesLocalDataSource(dao = dao, logger = mockLogger)
        val testObservable = notesLocalDataSource.getAllNotes().test()

        testObservable.assertValue(notes)
        testObservable.dispose()

        verify(exactly = 1) { dao.getAll() }
    }

    @Test
    fun `getNoteById fetches from local database`() {
        val id = 123L
        val note = createTestNote(id)
        val dao = mockk<NotesDao> {
            every { getById(any()) } returns Single.just(note)
        }
        val notesLocalDataSource = NotesLocalDataSource(dao = dao, logger = mockLogger)
        val testSingle = notesLocalDataSource.getNoteById(id).test()

        testSingle.assertValue(note)
        testSingle.dispose()

        verify(exactly = 1) { dao.getById(id) }
    }

    @Test
    fun `saveNote saves to local database`() {
        val note = createTestNote()
        val dao = mockk<NotesDao> {
            every { insert(any()) } returns Completable.complete()
        }
        val notesLocalDataSource = NotesLocalDataSource(dao = dao, logger = mockLogger)
        val testCompletable = notesLocalDataSource.saveNote(note).test()

        testCompletable.assertComplete()
        testCompletable.dispose()

        verify(exactly = 1) { dao.insert(note) }
    }

    @Test
    fun `deleteNoteById deletes from local database`() {
        val id = 1337L
        val dao = mockk<NotesDao> {
            every { deleteById(any()) } returns Completable.complete()
        }
        val notesLocalDataSource = NotesLocalDataSource(dao = dao, logger = mockLogger)
        val testCompletable = notesLocalDataSource.deleteNoteById(id).test()

        testCompletable.assertComplete()
        testCompletable.dispose()

        verify(exactly = 1) { dao.deleteById(id) }
    }
}