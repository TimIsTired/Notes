package com.timistired.notes.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timistired.notes.data.model.NotePreview
import com.timistired.notes.data.notes.INotesRepository
import com.timistired.notes.util.Constants
import com.timistired.notes.util.log.ILogger
import com.timistired.notes.util.sharedPreferences.ISharedPrefHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OverviewViewModel(
    notesRepository: INotesRepository,
    logger: ILogger,
    private val sharedPrefHelper: ISharedPrefHelper
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val _notePreviews: MutableLiveData<List<NotePreview>> = MutableLiveData()
    val notePreviews: LiveData<List<NotePreview>> get() = _notePreviews

    val coachmarksAlreadyShown: Boolean
        get() = sharedPrefHelper.getBoolean(Constants.KEY_COACHMARKS_SHOWN)

    init {
        disposables.add(
            notesRepository.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ previews ->
                    _notePreviews.postValue(previews)
                }, { error ->
                    logger.logError(TAG, error)
                })
        )
    }

    fun onCoachmarksShown() {
        sharedPrefHelper.saveBoolean(key = Constants.KEY_COACHMARKS_SHOWN, value = true)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "OverviewVM"
    }
}