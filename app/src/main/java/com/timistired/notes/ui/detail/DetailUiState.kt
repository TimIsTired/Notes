package com.timistired.notes.ui.detail

enum class DetailUiState {

    /**
     * Default value, do nothing
     * */
    DEFAULT,

    /**
     * Loading state, show loading indicator
     * */
    LOADING,

    /**
     * Note deleted, navigate back
     * */
    NOTE_DELETED
}