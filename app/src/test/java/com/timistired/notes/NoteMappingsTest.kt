package com.timistired.notes

import com.timistired.notes.data.model.mapping.toPreviewModel
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NoteMappingsTest {

    @Test
    fun `toPreviewModel transforms entity to preview model`() {
        val expected = createTestNote()
        val actual = expected.toPreviewModel()

        actual.id shouldBeEqualTo expected.id
        actual.header shouldBeEqualTo expected.header
    }
}