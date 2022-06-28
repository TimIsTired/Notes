package com.timistired.notes.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timistired.notes.data.location.ILocationRepository
import com.timistired.notes.data.location.model.Location
import com.timistired.notes.data.notes.INotesRepository
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

    private val _uiStatus: MutableLiveData<CreateUiStatus> =
        MutableLiveData(CreateUiStatus.DEFAULT)
    val uiStatus: LiveData<CreateUiStatus> get() = _uiStatus

    fun save(header: String, description: String) {
        _uiStatus.postValue(CreateUiStatus.LOADING)
        disposables.add(
            notesRepository.createAndSaveNote(
                header = header,
                description = description,
                location = location
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiStatus.postValue(CreateUiStatus.GO_BACK)
                }, { error ->
                    logger.logError(TAG, error)
                })
        )
    }

    fun fetchLocation() {
        _uiStatus.postValue(CreateUiStatus.LOADING)
        disposables.add(
            locationRepository.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ location ->
                    this.location = location
                    _uiStatus.postValue(CreateUiStatus.LOCATION_SUCCESS)
                }, { error ->
                    logger.logError(TAG, error)
                    _uiStatus.postValue(CreateUiStatus.LOCATION_ERROR)
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