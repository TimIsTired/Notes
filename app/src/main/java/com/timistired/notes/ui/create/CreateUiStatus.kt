package com.timistired.notes.ui.create

enum class CreateUiStatus {

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
     * Navigate back
     * */
    GO_BACK
}
