package com.timistired.notes.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.timistired.notes.util.extensions.isFirstItemOf

/**
 * [RecyclerView.ItemDecoration] implementation that adds spacing on top of the first item of the
 * recyclerview list item.
 *
 * @property spacingTop the spacing to add
 * */
class TopSpacingDecoration(private val spacingTop: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (view isFirstItemOf parent) {
            outRect.top = spacingTop
        }
    }
}