package com.timistired.notes.mapping

import com.timistired.notes.data.notes.entity.Note
import com.timistired.notes.ui.detail.NoteFull
import com.timistired.notes.ui.overview.NotePreview

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