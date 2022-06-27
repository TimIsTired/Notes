package com.timistired.notes.ui.detail

import com.timistired.notes.data.location.model.Location

/**
 * One full note.
 * */
data class NoteFull(
    val id: Long,
    val header: String,
    val description: String,
    val location: Location?
)