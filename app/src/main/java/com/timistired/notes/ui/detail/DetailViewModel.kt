package com.timistired.notes.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timistired.notes.data.model.Location
import com.timistired.notes.data.notes.INotesRepository
import com.timistired.notes.util.dateHelper.IDateHelper
import com.timistired.notes.util.log.ILogger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class DetailViewModel(
    private val notesRepository: INotesRepository,
    private val logger: ILogger,
    private val dateHelper: IDateHelper
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var currentNoteId: Long? = null

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData()
    val uiState: LiveData<DetailUiState> get() = _uiState

    private val _header: MutableLiveData<String> = MutableLiveData()
    val header: LiveData<String> get() = _header

    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: LiveData<String> get() = _description

    private val _location: MutableLiveData<Location> = MutableLiveData()
    val location: LiveData<Location> get() = _location

    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> get() = _date

    /**
     * Initialize this view model with a given note id.
     *
     * @param noteId the ID of the note to load
     * */
    fun init(noteId: Long) {
        currentNoteId = noteId
        _uiState.postValue(DetailUiState.LOADING)
        disposables.add(
            notesRepository.getNoteById(id = noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ note ->
                    _uiState.postValue(DetailUiState.DEFAULT)
                    _header.postValue(note.header)
                    _description.postValue(note.description)
                    _date.postValue(note.creationDate.asString())
                    note.location?.let {
                        _location.postValue(it)
                    }
                }, { error ->
                    logger.logError(TAG, error)
                })
        )
    }

    /**
     * Deletes this note
     * */
    fun deleteNote() {
        val id = currentNoteId ?: return
        _uiState.postValue(DetailUiState.LOADING)
        disposables.add(
            notesRepository.deleteNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiState.postValue(DetailUiState.NOTE_DELETED)
                }, { error ->
                    logger.logError(TAG, error)
                })
        )
    }

    /**
     * Convert this date to a string.
     * */
    private fun Date.asString(): String {
        return dateHelper.getDateAsString(this)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "DetailVM"
    }
}