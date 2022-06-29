package com.timistired.notes

import com.timistired.notes.data.model.Location
import com.timistired.notes.data.model.Note
import java.util.*
import kotlin.random.Random

/**
 * Creates a [Note] with randomized data for tests.
 *
 * @param id (optional) id to use. If not provided, a random id will be generated
 * @return the created test note
 * */
fun createTestNote(id: Long? = null): Note = Note(
    id = id ?: Random.nextLong(),
    header = "Test header ${Random.nextInt()}",
    description = "Test description ${Random.nextInt()}",
    location = Location(
        latitude = Random.nextDouble(),
        longitude = Random.nextDouble()
    ),
    creationDate = Date()
)