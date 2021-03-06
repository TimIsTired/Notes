package com.timistired.notes.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timistired.notes.data.location.ILocationRepository
import com.timistired.notes.data.model.Location
import com.timistired.notes.data.notes.INotesRepository
import com.timistired.notes.ui.create.CreateUiState.*
import com.timistired.notes.util.Event
import com.timistired.notes.util.log.ILogger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CreateViewModel(
    private val notesRepository: INotesRepository,
    private val locationRepository: ILocationRepository,
    private val logger: ILogger
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var location: Location? = null

    private val _uiState: MutableLiveData<Event<CreateUiState>> =
        MutableLiveData(Event(DEFAULT))
    val uiState: LiveData<Event<CreateUiState>> get() = _uiState

    fun save(header: String, description: String) {
        _uiState.postValue(Event(LOADING))
        disposables.add(
            notesRepository.createAndSaveNote(
                header = header,
                description = description,
                location = location
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiState.postValue(Event(NOTE_SAVED))
                }, { error ->
                    logger.logError(TAG, error)
                })
        )
    }

    fun fetchLocation() {
        _uiState.postValue(Event(LOADING))
        disposables.add(
            locationRepository.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ location ->
                    this.location = location
                    _uiState.postValue(Event(LOCATION_SUCCESS))
                }, { error ->
                    logger.logError(TAG, error)
                    _uiState.postValue(Event(LOCATION_ERROR))
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "CreateVM"
    }
}