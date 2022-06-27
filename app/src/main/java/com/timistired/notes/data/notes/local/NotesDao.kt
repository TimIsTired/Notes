package com.timistired.notes.data.notes.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.timistired.notes.data.notes.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface NotesDao {

    @Query("SELECT * FROM note ORDER BY creationDate ASC")
    fun getAll(): Observable<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: Long): Single<Note>

    @Insert
    fun insert(note: Note): Completable

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteById(id: Long): Completable
}