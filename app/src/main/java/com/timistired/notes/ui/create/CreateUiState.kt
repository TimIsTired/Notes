package com.timistired.notes.ui.create

enum class CreateUiState {

    /**
     * Default value, do nothing
     * */
    DEFAULT,

    /**
     * Show loading indicator
     * */
    LOADING,

    /**
     * Location successfully fetched, show success indicator
     * */
    LOCATION_SUCCESS,

    /**
     * Error while fetching location, show error indicator
     */
    LOCATION_ERROR,

    /**
     * Note saved, navigate back
     * */
    NOTE_SAVED
}
