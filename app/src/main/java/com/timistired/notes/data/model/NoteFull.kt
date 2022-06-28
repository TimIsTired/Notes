package com.timistired.notes.data.model

/**
 * One full note.
 * */
data class NoteFull(
    val id: Long,
    val header: String,
    val description: String,
    val location: Location?
)