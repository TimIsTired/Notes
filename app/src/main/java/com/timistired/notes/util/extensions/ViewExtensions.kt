package com.timistired.notes.util.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Show this view.
 * */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Hide this view.
 * */
fun View.hide() {
    visibility = View.GONE
}

/**
 * Checks if this view is the first item of a given [RecyclerView].
 *
 * @param parent the parent recycler view
 * @return true if this view is the first item, else false
 * */
infix fun View.isFirstItemOf(parent: RecyclerView): Boolean {
    return parent.getChildAdapterPosition(this) == 0
}