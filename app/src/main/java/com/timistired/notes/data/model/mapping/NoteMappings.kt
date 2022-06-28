package com.timistired.notes.data.model.mapping

import com.timistired.notes.data.model.Note
import com.timistired.notes.data.model.NoteFull
import com.timistired.notes.data.model.NotePreview

fun Note.toPreviewModel(): NotePreview = NotePreview(
    id = this.id,
    header = this.header
)

fun Note.toFullModel(): NoteFull = NoteFull(
    id = this.id,
    header = this.header,
    description = this.description,
    location = this.location
)