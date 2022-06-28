package com.timistired.notes.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Represents one note entity.
 * */
@Entity(tableName = "note")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val header: String,

    val description: String,

    @Embedded
    val location: Location? = null,

    val creationDate: Date
)
