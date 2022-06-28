package com.timistired.notes.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timistired.notes.data.model.NoteFull
import com.timistired.notes.data.notes.INotesRepository
import com.timistired.notes.data.model.mapping.toFullModel
import com.timistired.notes.util.log.ILogger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailViewModel(
    private val notesRepository: INotesRepository,
    private val logger: ILogger
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _note: MutableLiveData<NoteFull> = MutableLiveData()
    val note: LiveData<NoteFull> get() = _note

    fun init(noteId: Long) {
        disposables.add(
            notesRepository.getNoteById(id = noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ noteFull ->
                    _note.postValue(noteFull)
                }, { error ->
                    logger.logError(TAG, error)
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "DetailVM"
    }
}