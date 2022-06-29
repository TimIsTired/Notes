package com.timistired.notes.data.model.mapping

import com.timistired.notes.data.model.Note
import com.timistired.notes.data.model.NotePreview

fun Note.toPreviewModel(): NotePreview = NotePreview(
    id = this.id,
    header = this.header
)